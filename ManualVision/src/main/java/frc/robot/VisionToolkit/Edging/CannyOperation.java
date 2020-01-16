package frc.robot.VisionToolkit.Edging;

import java.util.Map;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.robot.VisionToolkit.Operation;

public class CannyOperation extends Operation {

    private Double lowThreshold;

    private NetworkTableEntry highThresholdEntry;

    private Mat cannyResult;
    private Mat mergedSrcResult;

    private static final int THRESHOLDRANGELOW = 0;
    private static final int THRESHOLDRANGEHIGH = 100;
    private static final int RATIO = 3;
    private static final int CANNYKERNELSIZE = 3;

    public CannyOperation(String operationName, int width, int height) {
        super(operationName, PixelFormat.kBGR, width, height);

        cannyResult = new Mat();
        mergedSrcResult = new Mat(new Size(width, height), CvType.CV_8UC3, Scalar.all(0));
    }

    @Override
    public void performOperation(Mat src, Mat dst) {
        //A terrible solution to a stupid problem...
        mergedSrcResult.release();
        mergedSrcResult = new Mat(src.size(), CvType.CV_8UC3, Scalar.all(0));
        
        Imgproc.Canny(src, cannyResult, lowThreshold, lowThreshold * RATIO, CANNYKERNELSIZE);
        src.copyTo(mergedSrcResult, cannyResult);
        mergedSrcResult.copyTo(dst);

        visualize(dst);
    }

    @Override
    protected void buildShuffleboardUI() {
        lowThreshold = Double.valueOf(THRESHOLDRANGELOW);

        highThresholdEntry = operationTab.add(operationName + " High Threshold", lowThreshold * RATIO)
            .withWidget(BuiltInWidgets.kNumberBar)
            .withProperties(Map.of("min", THRESHOLDRANGELOW, "max", THRESHOLDRANGEHIGH * RATIO))
            .getEntry();

        operationTab.add(operationName + " Low Threshold", lowThreshold)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", THRESHOLDRANGELOW, "max", THRESHOLDRANGEHIGH))
            .getEntry()
            .addListener((e) -> {
                lowThreshold = e.getEntry().getDouble(THRESHOLDRANGELOW);
                highThresholdEntry.setDouble(lowThreshold * RATIO);
            }, EntryListenerFlags.kUpdate);
    }
    
}