package frc.robot.VisionToolkit.Edging;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.VideoMode.PixelFormat;
import frc.robot.VisionToolkit.Operation;

public class FindContoursOperation extends Operation<Mat, Mat> {

    private List<MatOfPoint> contours;
    private Mat hierarchy;
    private Mat drawing;

    public FindContoursOperation(String operationName, int width, int height) {
        super(operationName, PixelFormat.kGray, width, height);

        contours = new ArrayList<MatOfPoint>();
        hierarchy = new Mat();
        drawing = new Mat();
    }

    @Override
    public void performOperation(Mat src, Mat dst) {
        contours.clear();
        hierarchy.release();
        hierarchy = new Mat();
        drawing.release();

        Imgproc.findContours(src, contours, hierarchy, Imgproc.RETR_TREE, 
            Imgproc.CHAIN_APPROX_SIMPLE);

        drawing = Mat.zeros(src.size(), CvType.CV_8UC3);

        for(int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(drawing, contours, i, new Scalar(255, 255, 255), 
                2, Core.LINE_8, hierarchy, 0, new Point());
        }

        Imgproc.cvtColor(drawing, dst, Imgproc.COLOR_BGR2GRAY);

        visualize(dst);
    }

    @Override
    protected void buildShuffleboardUI() {
        //No UI for this
    }
    
}