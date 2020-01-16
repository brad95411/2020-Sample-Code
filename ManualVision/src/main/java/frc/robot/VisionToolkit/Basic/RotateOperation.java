package frc.robot.VisionToolkit.Basic;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.VisionToolkit.Operation;

public class RotateOperation extends Operation {
    private SendableChooser<String> rotateType;

    public RotateOperation(String operationName, int width, int height) {
        super(operationName, PixelFormat.kBGR, width, height);
    }

    @Override
    public void performOperation(Mat src, Mat dst) {
        switch(rotateType.getSelected()) {
            case "Rotate 90":
                Core.transpose(src, dst);
                Core.flip(dst, dst, 1);
                break;
            case "Rotate 180":
                Core.flip(src, dst, -1);
                break;
            case "Rotate 270":
                Core.transpose(src, dst);
                Core.flip(dst, dst, 0);
                break;
            case "No Rotate":
            default:
                src.copyTo(dst);
                break;
        }

    }

    @Override
    protected void buildShuffleboardUI() {
        rotateType = new SendableChooser<String>();
        rotateType.setDefaultOption("No Rotate", "No Rotate");
        rotateType.addOption("Rotate 90", "Rotate 90");
        rotateType.addOption("Rotate 180", "Rotate 180");
        rotateType.addOption("Rotate 270", "Rotate 270");

        operationTab.add(operationName + " Rotate Mode", rotateType)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withSize(2, 1);
    }
    
}