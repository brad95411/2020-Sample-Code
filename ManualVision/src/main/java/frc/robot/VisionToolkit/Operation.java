package frc.robot.VisionToolkit;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public abstract class Operation {
    private MjpegServer server;
    private CvSource source;
    protected String operationName;

    public Operation(String operationName, PixelFormat outputFormat, int width, int height) {
        this.operationName = operationName;
        
        server = CameraServer.getInstance().addServer(operationName);

        source = new CvSource(operationName, new VideoMode(outputFormat, width, height, -1));
        server.setSource(source);
        
        Shuffleboard.getTab(operationName)
            .add(operationName, server.getSource())
            .withWidget(BuiltInWidgets.kCameraStream);
    }

    public abstract void performOperation(Mat src, Mat dst);

    protected void visualize(Mat toVisualize) {
        source.putFrame(toVisualize);
    }
}