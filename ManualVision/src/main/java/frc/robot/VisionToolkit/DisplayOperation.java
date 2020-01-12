package frc.robot.VisionToolkit;

import org.opencv.core.Mat;

import edu.wpi.cscore.VideoMode.PixelFormat;

public class DisplayOperation extends Operation {
    public DisplayOperation(String operationName, PixelFormat outputFormat, int width, int height) {
        super(operationName, outputFormat, width, height);
    }

    public void performOperation(Mat src, Mat dst) {
        visualize(src);
    }
}