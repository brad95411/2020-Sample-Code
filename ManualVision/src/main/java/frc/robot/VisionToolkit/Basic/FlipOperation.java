package frc.robot.VisionToolkit.Basic;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.VisionToolkit.Operation;

public class FlipOperation extends Operation {
    private SendableChooser<String> flipType;

    public FlipOperation(String operationName, int width, int height) {
        super(operationName, PixelFormat.kBGR, width, height);
    }

    @Override
    public void performOperation(Mat src, Mat dst) {
        switch(flipType.getSelected()) {
            case "Flip Horizontal":
                Core.flip(src, dst, 1);
                break;
            case "Flip Vertical":
                Core.flip(src, dst, 0);
                break;
            case "Flip Both":
                Core.flip(src, dst, -1);
                break;
            case "No Flip":
            default:
                src.copyTo(dst);
                break;
        }

        visualize(dst);
    }

    @Override
    protected void buildShuffleboardUI() {
        flipType = new SendableChooser<String>();
        flipType.setDefaultOption("No Flip", "No Flip");
        flipType.addOption("Flip Horizontal", "Flip Horizontal");
        flipType.addOption("Flip Vertical", "Flip Vertical");
        flipType.addOption("Flip Both", "Flip Both");

        operationTab.add(operationName + " Flip Mode", flipType)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withSize(2, 1);
    }
    
}