
package frc.robot;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;


public class Robot extends TimedRobot {
  private ColorSensorV3 colorSensor;

  private static final Color REVBLUETARGET = ColorMatch.makeColor(.143, .427, .429);
  private static final Color REVGREENTARGET = ColorMatch.makeColor(.197, .561, .240);
  private static final Color REVREDTARGET = ColorMatch.makeColor(.561, .232, .114);
  private static final Color REVYELLOWTARGET = ColorMatch.makeColor(.361, .524, .113);
  private static final Color OURBLUETARGET = ColorMatch.makeColor(.18, .44, .37);
  private static final Color OURGREENTARGET = ColorMatch.makeColor(.23, .54, .22);
  private static final Color OURREDTARGET = ColorMatch.makeColor(.55, .32, .1);
  private static final Color OURYELLOWTARGET = ColorMatch.makeColor(.39, .5, .1);

  private ColorMatch revMatcher;
  private ColorMatch ourMatcher;

  @Override
  public void robotInit() {
    revMatcher = new ColorMatch();
    revMatcher.addColorMatch(REVBLUETARGET);
    revMatcher.addColorMatch(REVGREENTARGET);
    revMatcher.addColorMatch(REVREDTARGET);
    revMatcher.addColorMatch(REVYELLOWTARGET);

    ourMatcher = new ColorMatch();
    ourMatcher.addColorMatch(OURBLUETARGET);
    ourMatcher.addColorMatch(OURGREENTARGET);
    ourMatcher.addColorMatch(OURREDTARGET);
    ourMatcher.addColorMatch(OURYELLOWTARGET);
  }

  @Override
  public void robotPeriodic() {
    Color revDetectedColor = colorSensor.getColor();
    Color ourDetectedColor = colorSensor.getColor();

    ColorMatchResult revResult = revMatcher.matchClosestColor(revDetectedColor);
    ColorMatchResult ourResult = ourMatcher.matchClosestColor(ourDetectedColor);

    String revColorString = getColorString(revResult, REVBLUETARGET, REVREDTARGET, REVGREENTARGET, REVYELLOWTARGET);
    String ourColorString = getColorString(ourResult, OURBLUETARGET, OURREDTARGET, OURGREENTARGET, OURYELLOWTARGET);
  
    SmartDashboard.putString("REV Color Match", revColorString);
    SmartDashboard.putString("Our Color Match", ourColorString);
    SmartDashboard.putBoolean("Detected The Same Colors?", revColorString.equals(ourColorString));
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

  private String getColorString(ColorMatchResult result, Color blueColor, Color redColor,
    Color greenColor, Color yellowColor) {

    if(result.color == blueColor) {
      return "Blue";
    } else if(result.color == redColor) {
      return "Red"; 
    } else if(result.color == greenColor) {
      return "Green";
    } else if(result.color == yellowColor) {
      return "Yellow";
    } else {
      return "Unknown";
    }
  }
}
