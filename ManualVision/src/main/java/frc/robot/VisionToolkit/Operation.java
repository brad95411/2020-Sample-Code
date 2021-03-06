package frc.robot.VisionToolkit;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.SendableCameraWrapper;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public abstract class Operation<S, D> {
    private MjpegServer server;
    private CvSource source;

    protected String operationName;
    protected ShuffleboardTab operationTab;

    public Operation(String operationName, PixelFormat outputFormat, int width, int height) {
        this.operationName = operationName;
        
        server = CameraServer.getInstance().addServer(operationName);

        source = new CvSource(operationName, new VideoMode(outputFormat, width, height, -1));
        server.setSource(source);

        operationTab = Shuffleboard.getTab(operationName + " Tab");
        
        operationTab.add(operationName, SendableCameraWrapper.wrap(server.getSource()))
            .withWidget(BuiltInWidgets.kCameraStream);

        buildShuffleboardUI();
    }

    public abstract void performOperation(S src, D dst);
    protected abstract void buildShuffleboardUI();

    protected void visualize(Mat toVisualize) {
        source.putFrame(toVisualize);
    }
}