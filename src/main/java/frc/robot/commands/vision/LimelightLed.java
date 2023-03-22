package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.limelight.Limelight;

public class LimelightLed extends CommandBase{
    
    boolean enabled;

    boolean done = false;

    public LimelightLed(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void initialize() {
        if (enabled == true) {
            Limelight.TurnLimelightOn();
        } else {
            Limelight.TurnLimelightOff();
        }
        done = true;
    }

    @Override
    public void end(boolean interrupted) {
        done = false;
    }

    @Override
    public boolean isFinished() {
        return done;
    }

}
