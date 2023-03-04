package frc.robot.commands.subcommandsaux;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class IntakeOpenClose extends CommandBase {

  IntakeSystem intakeSystem;
  boolean enabled;

  boolean done = false;

  public IntakeOpenClose(IntakeSystem intakeSystem, boolean enabled) {
    this.intakeSystem = intakeSystem;
    this.enabled = enabled;

    addRequirements(intakeSystem);
  }

  @Override
  public void initialize() {
    intakeSystem.EnableIntakeSolenoid(enabled);
    done = true;
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return done;
  }
}
