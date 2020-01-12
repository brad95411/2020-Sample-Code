/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;


public class Robot extends TimedRobot {
  


  @Override
  public void robotInit() {
    Shuffleboard.getTab("Shuffleboard")
      .add("Test", 1)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 360))
      .getEntry()
      .addListener((e) -> {
        System.out.println(e.getEntry().getNumber(1));
      }, EntryListenerFlags.kUpdate);
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
