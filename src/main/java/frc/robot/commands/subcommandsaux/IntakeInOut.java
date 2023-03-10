package frc.robot.commands.subcommandsaux;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class IntakeInOut extends CommandBase {

  IntakeSystem intakeSystem;
  double power;
  boolean reversed;
  boolean autonMode;

  boolean done;

  public IntakeInOut(IntakeSystem intakeSystem, double power, boolean reversed) {
    this.intakeSystem = intakeSystem;
    this.power = power;
    this.reversed = reversed;

    addRequirements(intakeSystem);
  }

  @Override
  public void initialize() {

  }

  @Override 
  public void execute() {
    intakeSystem.IntakeOn(power, reversed);
  }

  @Override
  public void end(boolean interrupted) {
    intakeSystem.IntakeOff();
  }

  @Override
  public boolean isFinished() {
    return false;
  }



}