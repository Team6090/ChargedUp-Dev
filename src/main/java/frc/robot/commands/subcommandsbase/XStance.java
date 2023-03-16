package frc.robot.commands.subcommandsbase;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class XStance extends CommandBase {

  Drivetrain drivetrain;
  boolean enabled;

  boolean done;

  public XStance(Drivetrain drivetrain, boolean enabled) {
    this.drivetrain = drivetrain;
    this.enabled = enabled;

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    if (enabled == true) {
      drivetrain.enableXstance();
    } else {
      drivetrain.disableXstance();
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
