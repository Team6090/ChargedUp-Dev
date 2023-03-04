package frc.robot.commands.subcommandsaux;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ArmHold extends CommandBase {

  PivotSystem pivotSystem;
  boolean hold;

  public ArmHold(PivotSystem pivotSystem, boolean hold) {
    this.pivotSystem = pivotSystem;
    this.hold = hold;

    addRequirements(pivotSystem);
  }

  @Override
  public void initialize() {

    pivotSystem.HoldArm(hold);
  }

  @Override
  public void execute() {
    // armPivot = pivotSystem.GetArmPivot();
  }

  @Override
  public void end(boolean interrupted) {
    // this.pivotSystem.HoldArm(false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
