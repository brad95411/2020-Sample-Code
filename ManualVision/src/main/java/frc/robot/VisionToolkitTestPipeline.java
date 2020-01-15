package frc.robot;

import org.opencv.core.Mat;

import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.vision.VisionPipeline;
import frc.robot.VisionToolkit.BoxBlurOperation;
import frc.robot.VisionToolkit.DilateErodeOperation;
import frc.robot.VisionToolkit.DisplayOperation;

public class VisionToolkitTestPipeline implements VisionPipeline {
    private DisplayOperation displaySource;
    private BoxBlurOperation boxBlurOperation;
    private DilateErodeOperation dilateErodeOperation;
    private Mat blurResult, dilateErodeResult;

    public VisionToolkitTestPipeline(int width, int height) {
        displaySource = new DisplayOperation("Source", PixelFormat.kBGR, width, height);
        boxBlurOperation = new BoxBlurOperation("Box Blur Test", width, height);
        dilateErodeOperation = new DilateErodeOperation("DilateErode Test", width, height);
        blurResult = new Mat();
        dilateErodeResult = new Mat();
    }

    public void process(Mat image) {
        displaySource.performOperation(image, null);
        boxBlurOperation.performOperation(image, blurResult);
        dilateErodeOperation.performOperation(image, dilateErodeResult);
    }
}