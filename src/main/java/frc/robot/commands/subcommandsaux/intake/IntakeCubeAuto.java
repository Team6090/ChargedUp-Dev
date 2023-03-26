package frc.robot.commands.subcommandsaux.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class IntakeCubeAuto extends CommandBase {

  IntakeSystem intakeSystem;

  Timer timer;

  boolean reversed;
  boolean done = false;

  public IntakeCubeAuto(IntakeSystem intakeSystem, boolean reversed) {
    this.intakeSystem = intakeSystem;
    this.reversed = reversed;

    timer = new Timer();

    addRequirements(intakeSystem);
  }

  @Override
  public void initialize() {
    timer.start();
    if (reversed = true) {
      intakeSystem.EnableIntakeSolenoid(false);
      intakeSystem.IntakeOn(0.75, true);
    } else {
      intakeSystem.EnableIntakeSolenoid(false);
      intakeSystem.IntakeOn(0.75, false);
    }
  }

  @Override
  public void execute() {
    if (IntakeSystem.ObjectInIntake() == false || timer.get() < .5) {

    } else {
      done = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    done = false;
    timer.stop();
    timer.reset();
    intakeSystem.IntakeOff();
    intakeSystem.EnableIntakeSolenoid(true);
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
