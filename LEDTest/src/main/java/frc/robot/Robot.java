
package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends TimedRobot {
  private AddressableLED ledsLeft;
  private AddressableLED ledsRight;
  private AddressableLEDBuffer buffer;

  private int hsvShiftCounter;

  private double currentTime;

  @Override
  public void robotInit() {
    ledsLeft = new AddressableLED(7);
    ledsRight = new AddressableLED(8);
    buffer = new AddressableLEDBuffer(46);

    ledsLeft.setLength(buffer.getLength());
    ledsRight.setLength(buffer.getLength());
    ledsLeft.start();
    ledsRight.start();

    hsvShiftCounter = 0;
    currentTime = Timer.getFPGATimestamp();
  }

  public void robotPeriodic() {
    if(Timer.getFPGATimestamp() - currentTime > .03) {
      for(int i = 0; i < buffer.getLength(); i++) {
        buffer.setHSV(i, (i + hsvShiftCounter) % 180, 255, 255);
      }
  
      ledsLeft.setData(buffer);
      ledsRight.setData(buffer);
  
      hsvShiftCounter++;

      currentTime = Timer.getFPGATimestamp();
    }
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
