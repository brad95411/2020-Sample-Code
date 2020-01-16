package frc.robot.VisionToolkit.Thresholding;

import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import frc.robot.VisionToolkit.Operation;

public class RGBThresholdOperation extends Operation {

    private double redMin;
    private double redMax;
    private double greenMin;
    private double greenMax;
    private double blueMin;
    private double blueMax;

    private static final int RANGELOW = 0;
    private static final int RANGEHIGH = 255;
    private static final PixelFormat DEFAULTRGBTHRESHPIXELFORMAT = PixelFormat.kGray;

    public RGBThresholdOperation(String operationName, int width, int height) {
        super(operationName, DEFAULTRGBTHRESHPIXELFORMAT, width, height);
    }

    @Override
    public void performOperation(Mat src, Mat dst) {
        Core.inRange(src, new Scalar(blueMin, greenMin, redMin), 
            new Scalar(blueMax, greenMax, redMax), dst);

        visualize(dst);
    }

    @Override
    protected void buildShuffleboardUI() {
        redMin = greenMin = blueMin = RANGELOW;
        redMax = greenMax = blueMax = RANGEHIGH;

        ShuffleboardLayout redList = operationTab.getLayout(
            operationName + " Red Range", BuiltInLayouts.kList)
            .withSize(2, 2);

        ShuffleboardLayout greenList = operationTab.getLayout(
            operationName + " Green Range", BuiltInLayouts.kList)
            .withSize(2, 2);

        ShuffleboardLayout blueList = operationTab.getLayout(
            operationName + " Blue Range", BuiltInLayouts.kList)
            .withSize(2, 2);

        redList.add("Red Low", redMin)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", RANGELOW, "max", RANGEHIGH))
            .getEntry()
            .addListener((e) -> {
                if(e.getEntry().getDouble(RANGELOW) < redMax) {
                    redMin = e.getEntry().getDouble(RANGELOW);
                } else {
                    e.getEntry().setDouble(redMax);
                    redMin = redMax;
                }
            }, EntryListenerFlags.kUpdate);
        
        redList.add("Red High", redMax)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", RANGELOW, "max", RANGEHIGH))
            .getEntry()
            .addListener((e) -> {
                if(e.getEntry().getDouble(RANGEHIGH) > redMin) {
                    redMax = e.getEntry().getDouble(RANGEHIGH);
                } else {
                    e.getEntry().setDouble(redMin);
                    redMax = redMin;
                }
            }, EntryListenerFlags.kUpdate);

        greenList.add("Green Low", greenMin)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", RANGELOW, "max", RANGEHIGH))
            .getEntry()
            .addListener((e) -> {
                if(e.getEntry().getDouble(RANGELOW) < greenMax) {
                    greenMin = e.getEntry().getDouble(RANGELOW);
                } else {
                    e.getEntry().setDouble(greenMax);
                    greenMin = greenMax;
                }
            }, EntryListenerFlags.kUpdate);
        
        greenList.add("Green High", greenMax)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", RANGELOW, "max", RANGEHIGH))
            .getEntry()
            .addListener((e) -> {
                if(e.getEntry().getDouble(RANGEHIGH) > greenMin) {
                    greenMax = e.getEntry().getDouble(RANGEHIGH);
                } else {
                    e.getEntry().setDouble(greenMin);
                    greenMax = greenMin;
                }
            }, EntryListenerFlags.kUpdate);
        
        blueList.add("Blue Low", blueMin)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", RANGELOW, "max", RANGEHIGH))
            .getEntry()
            .addListener((e) -> {
                if(e.getEntry().getDouble(RANGELOW) < blueMax) {
                    blueMin = e.getEntry().getDouble(RANGELOW);
                } else {
                    e.getEntry().setDouble(blueMax);
                    blueMin = blueMax;
                }
            }, EntryListenerFlags.kUpdate);
        
        blueList.add("Blue High", blueMax)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", RANGELOW, "max", RANGEHIGH))
            .getEntry()
            .addListener((e) -> {
                if(e.getEntry().getDouble(RANGEHIGH) > blueMin) {
                    blueMax = e.getEntry().getDouble(RANGEHIGH);
                } else {
                    e.getEntry().setDouble(blueMin);
                    blueMax = blueMin;
                }
            }, EntryListenerFlags.kUpdate);
    }
    
}