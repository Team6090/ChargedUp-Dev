package frc.robot.commands.subcommandsaux;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class PivotArmO extends CommandBase {

  PivotSystem pivotSystem;
  double power;
  boolean reversed;

  public PivotArmO(PivotSystem pivotSystem, double power, boolean reversed) {
    this.pivotSystem = pivotSystem;
    this.power = power;
    this.reversed = reversed;

    addRequirements(pivotSystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    pivotSystem.PivotArm(power, reversed);
  }

  @Override
  public void end(boolean interrupted) {
    pivotSystem.PivotArm(0, false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
