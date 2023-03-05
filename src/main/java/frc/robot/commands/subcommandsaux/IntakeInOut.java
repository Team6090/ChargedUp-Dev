package frc.robot.commands.subcommandsaux;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class IntakeInOut extends CommandBase {

  IntakeSystem intakeSystem;
  double power;
  boolean reversed;
  boolean autonMode;

  boolean done;

  public IntakeInOut(IntakeSystem intakeSystem, double power, boolean reversed, boolean autonMode) {
    this.intakeSystem = intakeSystem;
    this.power = power;
    this.reversed = reversed;
    this.autonMode = autonMode;

    addRequirements(intakeSystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    intakeSystem.IntakeOn(power, reversed);

    // if (autonMode == true) {

    //   if (intakeSystem.ObjectInIntake() == false) {
    //     done = true;
    //   }
    // } else {
    //   // done = true;
    // }
  }

  @Override
  public void end(boolean interrupted) {
    intakeSystem.IntakeOff();
    super.end(interrupted);
  }

  @Override
  public boolean isFinished() {
    if (autonMode == true) {
      return done;
    } else {
      return false;
    }
  }
}
