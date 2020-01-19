
package frc.robot;

import java.util.HashMap;
import java.util.Map;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Debounce.DynamicState;
import edu.wpi.first.wpilibj.I2C;

public class Robot extends TimedRobot {
  private ColorSensorV3 colorSensor;
  
  private static final Color REVBLUETARGET = ColorMatch.makeColor(.143, .427, .429);
  private static final Color REVGREENTARGET = ColorMatch.makeColor(.197, .561, .240);
  private static final Color REVREDTARGET = ColorMatch.makeColor(.561, .232, .114);
  private static final Color REVYELLOWTARGET = ColorMatch.makeColor(.361, .524, .113);

  private ColorMatch revMatcher;

  private XboxController controller;
  private Debounce aButtonDebounce;
  
  private DriverStation ds;

  private Map<String, String> colorMap;

  private SpeedController controlDiskMotor;

  private boolean rotateDisk;

  private String gameSpecificData;

  @Override
  public void robotInit() {
    colorSensor = new ColorSensorV3(I2C.Port.kOnboard);

    //Setup the REV Color Matcher with the REV targets we specified above
    revMatcher = new ColorMatch();
    revMatcher.addColorMatch(REVBLUETARGET);
    revMatcher.addColorMatch(REVGREENTARGET);
    revMatcher.addColorMatch(REVREDTARGET);
    revMatcher.addColorMatch(REVYELLOWTARGET);

    controller = new XboxController(0);
    aButtonDebounce = new Debounce(controller, 1);

    controlDiskMotor = new VictorSP(0);

    ds = DriverStation.getInstance();

    colorMap = new HashMap<String, String>();
    colorMap.put("B", "Red");
    colorMap.put("R", "Blue");
    colorMap.put("G", "Yellow");
    colorMap.put("Y", "Green");

    rotateDisk = false;
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
    if(aButtonDebounce.getCurrentState() == DynamicState.kReleased && !rotateDisk) {
      gameSpecificData = ds.getGameSpecificMessage();

      if(gameSpecificData.length() != 0) {
        rotateDisk = true;
      }
    } else if(aButtonDebounce.getCurrentState() == DynamicState.kReleased && rotateDisk) {
      rotateDisk = false;
    }

    if(rotateDisk) {
      String goToColor = colorMap.get(gameSpecificData.substring(0, 1));

      String currentColor = getColorString(revMatcher.matchClosestColor(colorSensor.getColor()), 
        REVBLUETARGET, REVREDTARGET, REVGREENTARGET, REVYELLOWTARGET);

      if(!goToColor.equals(currentColor)) {
        controlDiskMotor.set(1);
      } else {
        rotateDisk = false;
        controlDiskMotor.set(0);
      }
    } else {
      controlDiskMotor.set(0);
    }
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
