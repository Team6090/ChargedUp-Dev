package frc.robot.commands.subcommandsaux.pivot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class PivotArmToNextDown extends CommandBase {

  PivotSystem pivotSystem;
  double allowedNextCommandDeg;
  double deg;

  double pos;
  boolean autonMode;

  boolean done = false;

  public PivotArmToNextDown(PivotSystem pivotSystem, double deg, double allowedNextCommandDeg, boolean autonMode) {
    this.pivotSystem = pivotSystem;
    this.allowedNextCommandDeg = allowedNextCommandDeg;
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
      if (pos < allowedNextCommandDeg) { // Only when setPos is less than than current pos
        done = true;
      }
    } else {
      done = false;
    }
  }

  @Override
  public void end(boolean interrupted) {
    done = false;
    if (autonMode == true) {

    } else {
      this.pivotSystem.PivotArm(0, false);
    }
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
