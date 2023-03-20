package frc.robot.commands.subcommandsaux.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class IntakeInAuto extends CommandBase {

  IntakeSystem intakeSystem;

  Timer timer;

  boolean done = false;

  public IntakeInAuto(IntakeSystem intakeSystem) {
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
    SmartDashboard.putNumber("ObjectType", intakeSystem.ObjectType());
    if (intakeSystem.ObjectInIntake() == false) {
      intakeSystem.IntakeOn(0.8, false);
    } else {
      done = true;
    }

    if (timer.get() > 1.0) {
      done = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    intakeSystem.IntakeOff();
    timer.stop();
    timer.reset();
    done = false;
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
