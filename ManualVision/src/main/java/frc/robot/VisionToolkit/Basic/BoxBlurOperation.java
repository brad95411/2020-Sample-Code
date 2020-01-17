package frc.robot.VisionToolkit.Basic;

import java.util.Map;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.robot.VisionToolkit.Operation;

public class BoxBlurOperation extends Operation<Mat, Mat> {

    private Double blurKernel;

    private static final int DEFAULTBLURKERNEL = 1;
    private static final int DEFAULTBLURKERNELMIN = 1;
    private static final int DEFAULTBLURKERNELMAX = 100;
    private static final PixelFormat DEFAULTBLUROUTPUTFORMAT = PixelFormat.kBGR;

    public BoxBlurOperation(String operationName, int width, int height) {
        super(operationName, DEFAULTBLUROUTPUTFORMAT, width, height);

        blurKernel = Double.valueOf(DEFAULTBLURKERNEL);
    }

    public void performOperation(Mat src, Mat dst) {
        Imgproc.blur(src, dst, new Size(blurKernel, blurKernel), new Point(-1, -1));
        visualize(dst);
    }

    @Override
    protected void buildShuffleboardUI() {
        operationTab.add(operationName + " Kernel Size", DEFAULTBLURKERNEL)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", DEFAULTBLURKERNELMIN, "max", DEFAULTBLURKERNELMAX))
            .getEntry()
            .addListener((e) -> {
                blurKernel = e.getEntry().getDouble(DEFAULTBLURKERNEL);
            }, EntryListenerFlags.kUpdate);
    }
}