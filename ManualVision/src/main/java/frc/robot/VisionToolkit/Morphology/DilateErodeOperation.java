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

public class DilateErodeOperation extends Operation {
    private SendableChooser<String> dilateOrErode;
    private SendableChooser<String> elementType;

    private Double kernel;

    private Mat element;

    public static final int DEFAULTKERNELSIZE = 1;
    public static final int DEFAULTKERNELSIZEMIN = 1;
    public static final int DEFAULTKERNELSIZEMAX = 50;
    public static final PixelFormat DEFAULTDILATEERODEPIXELFORMAT = PixelFormat.kBGR;

    public DilateErodeOperation(String operationName, int width, int height) {
        this(operationName, DEFAULTDILATEERODEPIXELFORMAT, width, height, DEFAULTKERNELSIZE, DEFAULTKERNELSIZEMIN, 
            DEFAULTKERNELSIZEMAX);
    }

    public DilateErodeOperation(String operationName, PixelFormat outputFormat, int width, int height, int defaultKernel, 
        int minKernel, int maxKernel) {
        super(operationName, outputFormat, width, height);

        dilateOrErode = new SendableChooser<String>();
        dilateOrErode.setDefaultOption("Dilate", "Dilate");
        dilateOrErode.addOption("Erode", "Erode");

        elementType = new SendableChooser<String>();
        elementType.setDefaultOption("Rectangle", "Rectangle");
        elementType.addOption("Cross", "Cross");
        elementType.addOption("Ellipse", "Ellipse");

        kernel = Double.valueOf(DEFAULTKERNELSIZE);

        operationTab.add(operationName + " ErodeDilate", dilateOrErode)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withSize(2, 1);
        
        operationTab.add(operationName + " Element Type", elementType)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withSize(2, 1);

        operationTab.add(operationName + " Kernel Size", kernel)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", minKernel, "max", maxKernel))
            .getEntry()
            .addListener((e) -> {
                kernel = e.getEntry().getDouble(DEFAULTKERNELSIZE);
            }, EntryListenerFlags.kUpdate);
            
    }

    public void performOperation(Mat src, Mat dst) {
        int elementTypeInt;

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

        switch(dilateOrErode.getSelected()) {
            default:
            case "Dilate":
                Imgproc.dilate(src, dst, element);
                break;
            case "Erode":
                Imgproc.erode(src, dst, element);
        }

        visualize(dst);
    }
}