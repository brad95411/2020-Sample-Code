
package frc.robot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.NetworkButton;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends TimedRobot {

  private File rawDataLog;
  private File percentageDataLog;

  private PrintWriter rawDataLogWriter;
  private PrintWriter percentageDataLogWriter;

  private ColorSensorV3 colorSensor;

  private SendableChooser<String> loggingChooser;
  private NetworkButton startButton;

  private Timer timer;

  private static final double SAMPLESPEEDDEFAULT = 1; // Seconds
  private static final int NUMBERSAMPLESDEFAULT = 100;
  private static final String TABNAME = "Color Sensor Data Logging";
  private static final String CSVHEADER = "R,G,B,Blue,Red,Green,Yellow";

  private double sampleSpeed;
  private int samples;
  private int currentSamples;
  private boolean sampling;

  @Override
  public void robotInit() {
    rawDataLog = new File(Filesystem.getOperatingDirectory(), "Raw Data Log.csv");
    percentageDataLog = new File(Filesystem.getOperatingDirectory(), "Percentage Data Log.csv");

    try {
      rawDataLogWriter = new PrintWriter(new FileWriter(rawDataLog));
      percentageDataLogWriter = new PrintWriter(new FileWriter(percentageDataLog));

      rawDataLogWriter.println(CSVHEADER);
      percentageDataLogWriter.println(CSVHEADER);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    colorSensor = new ColorSensorV3(I2C.Port.kOnboard);

    sampleSpeed = SAMPLESPEEDDEFAULT;
    samples = NUMBERSAMPLESDEFAULT;
    
    Shuffleboard.getTab(TABNAME)
      .add("Sample Speed", SAMPLESPEEDDEFAULT)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", .5, "max", 60))
      .getEntry().addListener((en) -> {
        if(!sampling) {
          sampleSpeed = en.getEntry().getDouble(SAMPLESPEEDDEFAULT);
          System.out.println("New Sample Speed: " + en.getEntry().getDouble(SAMPLESPEEDDEFAULT));
        }
      }, EntryListenerFlags.kUpdate);

    loggingChooser = new SendableChooser<String>();
    loggingChooser.setDefaultOption("Blue", "1,0,0,0");
    loggingChooser.addOption("Red", "0,1,0,0");
    loggingChooser.addOption("Green", "0,0,1,0");
    loggingChooser.addOption("Yellow", "0,0,0,1");

    Shuffleboard.getTab(TABNAME)
      .add("Color Sampled", loggingChooser)
      .withWidget(BuiltInWidgets.kComboBoxChooser);

    Shuffleboard.getTab(TABNAME)
      .add("Number of Samples", NUMBERSAMPLESDEFAULT)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 10, "max", 1000, "Block increment", 10))
      .getEntry().addListener((en) -> {
        if(!sampling) {
          samples = (int) en.getEntry().getDouble(NUMBERSAMPLESDEFAULT);
          System.out.println("New Number of Samples: " + en.getEntry().getDouble(NUMBERSAMPLESDEFAULT));
        }
      }, EntryListenerFlags.kUpdate);

    startButton = new NetworkButton(TABNAME, "Start Button");
    Shuffleboard.getTab(TABNAME)
      .add(startButton, "Start Button")
      .getEntry().addListener((en) -> {
        if(!sampling) {
          sampling = en.getEntry().getBoolean(false);
          System.out.println("Should be sampling? " + sampling);
          currentSamples = 0;
          timer.start();
        }
      }, EntryListenerFlags.kUpdate);
      
    timer = new Timer();

  }

  public void robotPeriodic() {
    if(sampling) {
      if(timer.get() > sampleSpeed) {
        if(currentSamples < samples) {
          int red = colorSensor.getRed();
          int green = colorSensor.getGreen();
          int blue = colorSensor.getBlue();
          double total = red + green + blue;

          double redPercentage = red / total * 100;
          double greenPercentage = green / total * 100;
          double bluePercentage = blue / total * 100;

          rawDataLogWriter.println("" + red + "," + green + "," + blue + loggingChooser.getSelected());
          percentageDataLogWriter.println("" + redPercentage + "," + greenPercentage + "," + bluePercentage + loggingChooser.getSelected());
        } else {
          timer.stop();
          sampling = false;
        }

        timer.reset();
      }
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
