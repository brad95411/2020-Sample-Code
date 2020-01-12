package frc.robot;

import org.opencv.core.Mat;

import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.vision.VisionPipeline;
import frc.robot.VisionToolkit.DisplayOperation;

public class VisionToolkitTestPipeline implements VisionPipeline {
    private DisplayOperation displaySource;

    public VisionToolkitTestPipeline(int width, int height) {
        displaySource = new DisplayOperation("Source", PixelFormat.kBGR, width, height);
    }

    public void process(Mat image) {
        displaySource.performOperation(image, null);
    }
}