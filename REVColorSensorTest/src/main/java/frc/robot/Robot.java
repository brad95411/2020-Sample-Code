package frc.robot;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {
  
  private ColorSensorV3 colorSensor;

  @Override
  public void robotInit() {
    colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
  }

  public void robotPeriodic() {
    int red = colorSensor.getRed();
    int green = colorSensor.getGreen();
    int blue = colorSensor.getBlue();

    double total = red + green + blue;
    double redPercentage = red / total * 100;
    double greenPercentage = green / total * 100;
    double bluePercentage = blue / total * 100;

    SmartDashboard.putNumber("Red", red);
    SmartDashboard.putNumber("Green", green);
    SmartDashboard.putNumber("Blue", blue);
    SmartDashboard.putNumber("IR", colorSensor.getIR());

    SmartDashboard.putNumber("Red Percentage", redPercentage);
    SmartDashboard.putNumber("Green Percentage", greenPercentage);
    SmartDashboard.putNumber("Blue Percentage", bluePercentage);

    if(withinTolerance(redPercentage, 39, 5) && withinTolerance(greenPercentage, 50, 5) && withinTolerance(bluePercentage, 10, 5)) {
      SmartDashboard.putString("Color", "Yellow");
    } else if(withinTolerance(redPercentage, 55, 5) && withinTolerance(greenPercentage, 32, 5) && withinTolerance(bluePercentage, 10, 5)) {
      SmartDashboard.putString("Color", "Red");
    } else if(withinTolerance(redPercentage, 23, 5) && withinTolerance(greenPercentage, 54, 5) && withinTolerance(bluePercentage, 22, 5)) {
      SmartDashboard.putString("Color", "Green");
    } else if(withinTolerance(redPercentage, 18, 5) && withinTolerance(greenPercentage, 44, 5) && withinTolerance(bluePercentage, 37, 5)) {
      SmartDashboard.putString("Color", "Blue");
    } else {
      SmartDashboard.putString("Color", "Unknown");
    }

    /*if(Math.max(Math.max(red, green), blue) == green) {
      SmartDashboard.putString("Color", "Green");
    } else if(red > green && green > blue) {
      SmartDashboard.putString("Color", "Red");
    } else if(Math.max(red, green) == green && red > blue && green > blue) {
      SmartDashboard.putString("Color", "Yellow");
    } else if(Math.abs(green - blue) < red) {
      SmartDashboard.putString("Color", "Blue");
    }*/
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

  public boolean withinTolerance(double value, double desired, double plusMinusRange) {
    if(value > desired - plusMinusRange && value < desired + plusMinusRange) {
      return true;
    } else {
      return false;
    }
  }
}
