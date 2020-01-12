package frc.robot;

import java.security.KeyStore.Entry;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SuppliedValueWidget;

public class ExperimentationPipeline implements VisionPipeline {

    private MjpegServer server;
    private MjpegServer bluredImage;
    private MjpegServer hsvThreshold;
    private CvSource source;
    private CvSource bluredImageSource;
    private CvSource hsvThresholdSource;
    private Mat hsvColorConverted;
    private Mat binaryThreshold;
    private Mat blured;

    private double hLow;
    private double sLow;
    private double vLow;
    private double hHigh;
    private double sHigh;
    private double vHigh;
    private double blurKernel;

    public ExperimentationPipeline(String name, int width, int height) {
        source = new CvSource(name, new VideoMode(PixelFormat.kBGR, width, height, -1));
        hsvThresholdSource = new CvSource(name + " HSV Threshold", new VideoMode(PixelFormat.kGray, width, height, -1));
        bluredImageSource = new CvSource(name + " Blured", new VideoMode(PixelFormat.kBGR, width, height, -1));

        server = CameraServer.getInstance().addServer(name);
        server.setSource(source);

        bluredImage = CameraServer.getInstance().addServer(name + " Blured");
        bluredImage.setSource(bluredImageSource);

        hsvThreshold = CameraServer.getInstance().addServer(name + " HSV Threshold");
        hsvThreshold.setSource(hsvThresholdSource);

        hsvColorConverted = new Mat();
        binaryThreshold = new Mat();
        blured = new Mat();

        hLow = 0;
        sLow = 0;
        vLow = 0;
        hHigh = 180;
        sHigh = 255;
        vHigh = 255;
        blurKernel = 1;

        Shuffleboard.getTab(name)
            .add("H Low", hLow)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 180))
            .getEntry()
            .addListener((e) -> {
                hLow = e.getEntry().getDouble(0);
            }, EntryListenerFlags.kUpdate);

        Shuffleboard.getTab(name)
            .add("H High", hHigh)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 180))
            .getEntry()
            .addListener((e) -> {
                hHigh = e.getEntry().getDouble(180);
            }, EntryListenerFlags.kUpdate);

        Shuffleboard.getTab(name)
            .add("S Low", sLow)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 255))
            .getEntry()
            .addListener((e) -> {
                sLow = e.getEntry().getDouble(0);
            }, EntryListenerFlags.kUpdate);

        Shuffleboard.getTab(name)
            .add("S High", sHigh)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 255))
            .getEntry()
            .addListener((e) -> {
                sHigh = e.getEntry().getDouble(255);
            }, EntryListenerFlags.kUpdate);
        
        Shuffleboard.getTab(name) 
            .add("V Low", vLow)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 255))
            .getEntry()
            .addListener((e) -> {
                vLow = e.getEntry().getDouble(0);
            }, EntryListenerFlags.kUpdate);

        Shuffleboard.getTab(name) 
            .add("V High", vHigh)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 255))
            .getEntry()
            .addListener((e) -> {
                vHigh = e.getEntry().getDouble(255);
            }, EntryListenerFlags.kUpdate);

        Shuffleboard.getTab(name) 
            .add("Blur", blurKernel)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 1, "max", 25, "Block increment", 1))
            .getEntry()
            .addListener((e) -> {
                blurKernel = e.getEntry().getDouble(1);
            }, EntryListenerFlags.kUpdate);
        
            
    }

    @Override
    public void process(Mat image) {
        Imgproc.blur(image, blured, new Size(blurKernel, blurKernel), new Point(-1, -1));

        Imgproc.cvtColor(blured, hsvColorConverted, Imgproc.COLOR_BGR2HSV);

        Core.inRange(hsvColorConverted, new Scalar(hLow, sLow, vLow), 
            new Scalar(hHigh, sHigh, vHigh), binaryThreshold);

        bluredImageSource.putFrame(blured);
        hsvThresholdSource.putFrame(binaryThreshold);
        source.putFrame(image);
    }
    
}