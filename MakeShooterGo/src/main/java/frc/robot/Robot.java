package frc.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Robot extends TimedRobot {

    private VictorSP vsp1;
    private VictorSP vsp2;

    private XboxController controller;

    private PowerDistributionPanel pdp;

    public void robotInit() {
        vsp1 = new VictorSP(2);
        vsp2 = new VictorSP(3);

        controller = new XboxController(0);

        pdp = new PowerDistributionPanel();

        Shuffleboard.getTab("SmartDashboard")
            .add(pdp);
    }

    public void robotPeriodic() {
        
    }

    public void teleopPeriodic() {
        if(controller.getAButton()) {
            vsp1.set(0.25);
            vsp2.set(0.25);
        } else {
            vsp1.set(0);
            vsp2.set(0);
        }
    }
}