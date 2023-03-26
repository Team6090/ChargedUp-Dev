package frc.robot.commands.subcommandsaux.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class IntakeInOutAuto extends CommandBase {

  IntakeSystem intakeSystem;

  boolean done = false;

  Timer timer;

  public IntakeInOutAuto(IntakeSystem intakeSystem) {
    this.intakeSystem = intakeSystem;

    timer = new Timer();

    addRequirements(intakeSystem);
  }

  @Override
  public void initialize() {
    timer.start();
  }

  @Override
  public void execute() {
    if (intakeSystem.ObjectInIntake() || timer.get() < 0.45) { // TODO: Log for proximity accuracy
      intakeSystem.IntakeOn(0.45, false);
    } else {
      done = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    timer.stop();
    timer.reset();
    intakeSystem.IntakeOff();
    done = false;
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
