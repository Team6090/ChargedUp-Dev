package frc.robot.commands.subcommandsaux.pivot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.PivotSubsystem;

public class PivotMove extends CommandBase {

  PivotSubsystem pivotSystem;
  double deg;

  double pos;
  boolean autonMode;

  boolean done = false;

  public PivotMove(PivotSubsystem pivotSystem, double deg, boolean autonMode) {
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
      if (pos > deg - 3 && pos < deg + 3) {
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
