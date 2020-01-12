
package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.vision.VisionRunner;
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  private CameraServer cameraServer;

  private UsbCamera sourceCamera;

  private VisionPipeline pipeline;

  private VisionRunner<VisionPipeline> runner;

  private VisionThread thread;
 
  @Override
  public void robotInit() {
    cameraServer = CameraServer.getInstance();
    sourceCamera = cameraServer.startAutomaticCapture(1);

    //pipeline = new ExperimentationPipeline("Exp Pipeline Output", sourceCamera.getVideoMode().width, sourceCamera.getVideoMode().height);
    pipeline = new VisionToolkitTestPipeline(sourceCamera.getVideoMode().width, sourceCamera.getVideoMode().height);

    runner = new VisionRunner<VisionPipeline>(sourceCamera, pipeline, (p) -> {
      
    });

    thread = new VisionThread(runner);
    thread.start();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
