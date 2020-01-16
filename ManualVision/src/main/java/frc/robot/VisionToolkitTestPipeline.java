package frc.robot;

import org.opencv.core.Mat;

import edu.wpi.first.vision.VisionPipeline;
import frc.robot.VisionToolkit.Basic.BoxBlurOperation;
import frc.robot.VisionToolkit.Basic.DisplayOperation;
import frc.robot.VisionToolkit.Morphology.DilateErodeOperation;

public class VisionToolkitTestPipeline implements VisionPipeline {
    private DisplayOperation displaySource;
    private BoxBlurOperation boxBlurOperation;
    private DilateErodeOperation dilateErodeOperation;
    private Mat blurResult, dilateErodeResult;

    public VisionToolkitTestPipeline(int width, int height) {
        displaySource = new DisplayOperation("Source", width, height);
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