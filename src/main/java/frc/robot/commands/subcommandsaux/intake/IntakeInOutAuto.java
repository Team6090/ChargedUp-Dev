package frc.robot.commands.subcommandsaux.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class IntakeInOutAuto extends CommandBase {

  IntakeSystem intakeSystem;

  boolean done = false;

  public IntakeInOutAuto(IntakeSystem intakeSystem) {
    this.intakeSystem = intakeSystem;

    addRequirements(intakeSystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if (intakeSystem.ObjectInIntake()) {
      intakeSystem.IntakeOn(0.6, true);
    } else {
      done = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    intakeSystem.IntakeOff();
    done = false;
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
