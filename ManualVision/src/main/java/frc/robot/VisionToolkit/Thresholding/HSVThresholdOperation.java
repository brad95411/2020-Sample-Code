package frc.robot.VisionToolkit.Thresholding;

import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import frc.robot.VisionToolkit.Operation;

public class HSVThresholdOperation extends Operation {

    private double hMin;
    private double hMax;
    private double sMin;
    private double sMax;
    private double vMin;
    private double vMax;

    private static final int HRANGELOW = 0;
    private static final int HRANGEHIGH = 180;
    private static final int SVRANGELOW = 0;
    private static final int SVRANGEHIGH = 255;
    private static final PixelFormat DEFAULTHSVTHRESHPIXELFORMAT = PixelFormat.kGray;

    public HSVThresholdOperation(String operationName, int width, int height) {
        super(operationName, DEFAULTHSVTHRESHPIXELFORMAT, width, height);
    }

    @Override
    public void performOperation(Mat src, Mat dst) {
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2HSV);
        Core.inRange(dst, new Scalar(hMin, sMin, vMin), 
            new Scalar(hMax, sMax, vMax), dst);

        visualize(dst);
    }

    @Override
    protected void buildShuffleboardUI() {
        hMin = HRANGELOW;
        hMax = HRANGEHIGH;

        sMin = vMin = SVRANGELOW;
        sMax = vMax = SVRANGEHIGH;

        ShuffleboardLayout hList = operationTab.getLayout(
            operationName + " H Range", BuiltInLayouts.kList)
            .withSize(2, 2);

        ShuffleboardLayout sList = operationTab.getLayout(
            operationName + " S Range", BuiltInLayouts.kList)
            .withSize(2, 2);

        ShuffleboardLayout vList = operationTab.getLayout(
            operationName + " V Range", BuiltInLayouts.kList)
            .withSize(2, 2);

        hList.add("H Low", hMin)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", HRANGELOW, "max", HRANGEHIGH))
            .getEntry()
            .addListener((e) -> {
                if(e.getEntry().getDouble(HRANGELOW) < hMax) {
                    hMin = e.getEntry().getDouble(HRANGELOW);
                } else {
                    e.getEntry().setDouble(hMax);
                    hMin = hMax;
                }
            }, EntryListenerFlags.kUpdate);
        
        hList.add("H High", hMax)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", HRANGELOW, "max", HRANGEHIGH))
            .getEntry()
            .addListener((e) -> {
                if(e.getEntry().getDouble(HRANGEHIGH) > hMin) {
                    hMax = e.getEntry().getDouble(HRANGEHIGH);
                } else {
                    e.getEntry().setDouble(hMin);
                    hMax = hMin;
                }
            }, EntryListenerFlags.kUpdate);

        sList.add("S Low", sMin)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", SVRANGELOW, "max", SVRANGEHIGH))
            .getEntry()
            .addListener((e) -> {
                if(e.getEntry().getDouble(SVRANGELOW) < sMax) {
                    sMin = e.getEntry().getDouble(SVRANGELOW);
                } else {
                    e.getEntry().setDouble(sMax);
                    sMin = sMax;
                }
            }, EntryListenerFlags.kUpdate);
        
        sList.add("S High", sMax)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", SVRANGELOW, "max", SVRANGEHIGH))
            .getEntry()
            .addListener((e) -> {
                if(e.getEntry().getDouble(SVRANGEHIGH) > sMin) {
                    sMax = e.getEntry().getDouble(SVRANGEHIGH);
                } else {
                    e.getEntry().setDouble(sMin);
                    sMax = sMin;
                }
            }, EntryListenerFlags.kUpdate);
        
        vList.add("V Low", vMin)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", SVRANGELOW, "max", SVRANGEHIGH))
            .getEntry()
            .addListener((e) -> {
                if(e.getEntry().getDouble(SVRANGELOW) < vMax) {
                    vMin = e.getEntry().getDouble(SVRANGELOW);
                } else {
                    e.getEntry().setDouble(vMax);
                    vMin = vMax;
                }
            }, EntryListenerFlags.kUpdate);
        
        vList.add("V High", vMax)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", SVRANGELOW, "max", SVRANGEHIGH))
            .getEntry()
            .addListener((e) -> {
                if(e.getEntry().getDouble(SVRANGEHIGH) > vMin) {
                    vMax = e.getEntry().getDouble(SVRANGEHIGH);
                } else {
                    e.getEntry().setDouble(vMin);
                    vMax = vMin;
                }
            }, EntryListenerFlags.kUpdate);
    }
    
}