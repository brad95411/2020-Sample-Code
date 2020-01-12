
package frc.robot;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class Robot extends TimedRobot {
  //The object representation of our color sensor
  private ColorSensorV3 colorSensor;

  //The various target Colors for our ColorMatch tool. We have 2 sets of the 4 vinyl colors
  //one that were defined in the REV ColorMatch example, the others our based on the values
  //we determined to be "correct". I assume (I am not sure) that ColorMatch.makeColor uses
  //percentages of the total color, so that's how this has been formatted
  private static final Color REVBLUETARGET = ColorMatch.makeColor(.143, .427, .429);
  private static final Color REVGREENTARGET = ColorMatch.makeColor(.197, .561, .240);
  private static final Color REVREDTARGET = ColorMatch.makeColor(.561, .232, .114);
  private static final Color REVYELLOWTARGET = ColorMatch.makeColor(.361, .524, .113);
  private static final Color OURBLUETARGET = ColorMatch.makeColor(.18, .44, .37);
  private static final Color OURGREENTARGET = ColorMatch.makeColor(.23, .54, .22);
  private static final Color OURREDTARGET = ColorMatch.makeColor(.55, .32, .1);
  private static final Color OURYELLOWTARGET = ColorMatch.makeColor(.39, .5, .1);

  //ColorMatch objects to do the actual matching with
  private ColorMatch revMatcher;
  private ColorMatch ourMatcher;

  @Override
  public void robotInit() {
    colorSensor = new ColorSensorV3(I2C.Port.kOnboard);

    //Setup the REV Color Matcher with the REV targets we specified above
    revMatcher = new ColorMatch();
    revMatcher.addColorMatch(REVBLUETARGET);
    revMatcher.addColorMatch(REVGREENTARGET);
    revMatcher.addColorMatch(REVREDTARGET);
    revMatcher.addColorMatch(REVYELLOWTARGET);

    //Setup our Color Matcher with the targets we determined
    ourMatcher = new ColorMatch();
    ourMatcher.addColorMatch(OURBLUETARGET);
    ourMatcher.addColorMatch(OURGREENTARGET);
    ourMatcher.addColorMatch(OURREDTARGET);
    ourMatcher.addColorMatch(OURYELLOWTARGET);
  }

  @Override
  public void robotPeriodic() {
    //Get the color value from the sensor
    Color detectedColor = colorSensor.getColor();

    //Use the color matchers to get the supposed color that each matcher "saw" from the detected color
    ColorMatchResult revResult = revMatcher.matchClosestColor(detectedColor);
    ColorMatchResult ourResult = ourMatcher.matchClosestColor(detectedColor);

    //Get the proper name of the detected color
    String revColorString = getColorString(revResult, REVBLUETARGET, REVREDTARGET, REVGREENTARGET, REVYELLOWTARGET);
    String ourColorString = getColorString(ourResult, OURBLUETARGET, OURREDTARGET, OURGREENTARGET, OURYELLOWTARGET);
  
    //Put on the SmartDashboard/Shuffleboard the strings for each matcher and whether or not the detected value was the same
    //Also put the "confidence" or how certain the color match class is that the color that it returned is correct
    SmartDashboard.putString("REV Color Match", revColorString);
    SmartDashboard.putNumber("REV Color Match Confidence", revResult.confidence);
    SmartDashboard.putString("Our Color Match", ourColorString);
    SmartDashboard.putNumber("Our Color Match Confidence", ourResult.confidence);
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

  //Takes a color match result and 4 color objects to get the appropriate color string
  private String getColorString(ColorMatchResult result, Color blueColor, Color redColor,
    Color greenColor, Color yellowColor) {

    //Take the result color and compare it to each input color, returning the appropriate color
    //string name when necessary. Returns Unknown if the color coming in isn't recognized
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
