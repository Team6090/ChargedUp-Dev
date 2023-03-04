package frc.robot.commands.subcommandsaux;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class PivotMove extends CommandBase {

  PivotSystem pivotSystem;
  double deg;

  double pos;
  boolean autonMode;

  boolean done = false;

  public PivotMove(PivotSystem pivotSystem, double deg, boolean autonMode) {
    this.pivotSystem = pivotSystem;
    this.deg = deg;
    this.autonMode = autonMode;

    addRequirements(pivotSystem);
  }

  @Override
  public void initialize() {
    pivotSystem.PivotArmDeg(deg);
  }

  @Override
  public void execute() {
    if (autonMode == true) {
      pos = pivotSystem.GetArmDeg();
      if (pos > deg - 2 && pos < deg + 2) {
        done = true;
      }
    } else {
      done = false;
    }
  }

  @Override
  public void end(boolean interrupted) {
    if (autonMode == true) {
      super.cancel();
    } else {
      this.pivotSystem.PivotArm(0, false);
    }
  }

  @Override
  public boolean isFinished() {
    return done; // NO TALK
  }
}
