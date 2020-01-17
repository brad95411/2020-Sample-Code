package frc.robot.VisionToolkit.Basic;

import org.opencv.core.Mat;

import edu.wpi.cscore.VideoMode.PixelFormat;
import frc.robot.VisionToolkit.Operation;

public class DisplayOperation extends Operation<Mat, Mat> {
    private static final PixelFormat DEFAULTDISPLAYPIXELFORMAT = PixelFormat.kBGR;

    public DisplayOperation(String operationName, int width, int height) {
        super(operationName, DEFAULTDISPLAYPIXELFORMAT, width, height);
    }

    public void performOperation(Mat src, Mat dst) {
        visualize(src);
    }

    @Override
    protected void buildShuffleboardUI() {
        //There is no UI for the display operation...
    }
}