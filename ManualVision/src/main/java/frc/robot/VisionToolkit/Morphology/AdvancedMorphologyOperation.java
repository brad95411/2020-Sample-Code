package frc.robot.VisionToolkit.Morphology;

import java.util.Map;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.VisionToolkit.Operation;

public class AdvancedMorphologyOperation extends Operation<Mat, Mat> {
    private SendableChooser<String> morphType;
    private SendableChooser<String> elementType;

    private Double kernel;

    private Mat element;

    private static final int DEFAULTKERNELSIZE = 1;
    private static final int DEFAULTKERNELSIZEMIN = 1;
    private static final int DEFAULTKERNELSIZEMAX = 50;
    private static final PixelFormat DEFAULTADVMORPHPIXELFORMAT = PixelFormat.kBGR;

    public AdvancedMorphologyOperation(String operationName, int width, int height) {
        super(operationName, DEFAULTADVMORPHPIXELFORMAT, width, height);
    }

    @Override
    public void performOperation(Mat src, Mat dst) {
        int morphTypeInt;
        int elementTypeInt;

        switch(elementType.getSelected()) {
            default:
            case "Open":
                morphTypeInt = Imgproc.MORPH_OPEN;
                break;
            case "Close":
                morphTypeInt = Imgproc.MORPH_CLOSE;
                break;
            case "Gradient":
                morphTypeInt = Imgproc.MORPH_GRADIENT;
                break;
            case "Top Hat":
                morphTypeInt = Imgproc.MORPH_TOPHAT;
                break;
            case "Black Hat":
                morphTypeInt = Imgproc.MORPH_BLACKHAT;
                break;
        }

        switch(elementType.getSelected()) {
            default:
            case "Rectangle":
                elementTypeInt = Imgproc.CV_SHAPE_RECT;
                break;
            case "Cross":
                elementTypeInt = Imgproc.CV_SHAPE_CROSS;
                break;
            case "Ellipse":
                elementTypeInt = Imgproc.CV_SHAPE_ELLIPSE;
                break;
        }

        element = Imgproc.getStructuringElement(elementTypeInt, 
            new Size(2 * kernel + 1, 2 * kernel + 1), 
            new Point(kernel, kernel));

        Imgproc.morphologyEx(src, dst, morphTypeInt, element);

        visualize(dst);
    }

    @Override
    protected void buildShuffleboardUI() {
        morphType = new SendableChooser<String>();
        morphType.setDefaultOption("Open", "Open");
        morphType.addOption("Close", "Close");
        morphType.addOption("Gradient", "Gradient");
        morphType.addOption("Top Hat", "Top Hat");
        morphType.addOption("Black Hat", "Black Hat");

        elementType = new SendableChooser<String>();
        elementType.setDefaultOption("Rectangle", "Rectangle");
        elementType.addOption("Cross", "Cross");
        elementType.addOption("Ellipse", "Ellipse");

        kernel = Double.valueOf(DEFAULTKERNELSIZE);

        operationTab.add(operationName + " Morph Type", morphType)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withSize(2, 1);
        
        operationTab.add(operationName + " Element Type", elementType)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withSize(2, 1);

        operationTab.add(operationName + " Kernel Size", kernel)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", DEFAULTKERNELSIZEMIN, "max", DEFAULTKERNELSIZEMAX))
            .getEntry()
            .addListener((e) -> {
                kernel = e.getEntry().getDouble(DEFAULTKERNELSIZE);
            }, EntryListenerFlags.kUpdate);
    }
    
}