package frc.robot.VisionToolkit;

import java.util.Map;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class BoxBlurOperation extends Operation {

    private double blurKernel;

    public BoxBlurOperation(String operationName, PixelFormat outputFormat, int width, int height, int defaultValue, 
        int sliderLow, int sliderHigh) {
        super(operationName, outputFormat, width, height);

        Shuffleboard.getTab(operationName)
            .add(operationName, defaultValue)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", sliderLow, "max", sliderHigh))
            .getEntry()
            .addListener((e) -> {
                blurKernel = e.getEntry().getDouble(defaultValue);
            }, EntryListenerFlags.kUpdate);
    }

    public void performOperation(Mat src, Mat dst) {
        Imgproc.blur(src, dst, new Size(blurKernel, blurKernel), new Point(-1, -1));
        visualize(dst);
    }
}