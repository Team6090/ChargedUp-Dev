package frc.robot.commands.subcommandsaux;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class ExtendArmO extends CommandBase {
  IntakeSystem intakeSystem;
  double power;

  public ExtendArmO(IntakeSystem intakeSystem, double power) {
    this.intakeSystem = intakeSystem;
    this.power = power;

    addRequirements(intakeSystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    intakeSystem.ExtendArmPO(power);
  }

  @Override
  public void end(boolean interrupted) {
    this.intakeSystem.ExtendArmPO(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
