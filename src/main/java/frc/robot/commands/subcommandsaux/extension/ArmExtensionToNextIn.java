package frc.robot.commands.subcommandsaux.extension;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.commands.robot.LockArmExtend;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class ArmExtensionToNextIn extends CommandBase {

  TelescopeSystem telescopeSystem;
  double set;
  double allowedNextCommandSet;
  boolean autonMode;

  boolean done = false;

  public ArmExtensionToNextIn(
      TelescopeSystem telescopeSystem,
      double set,
      double allowedNextCommandSet,
      boolean autonMode) {
    this.telescopeSystem = telescopeSystem;
    this.set = set;
    this.allowedNextCommandSet = allowedNextCommandSet;
    this.autonMode = autonMode;

    addRequirements(telescopeSystem);
  }

  @Override
  public void initialize() {
    if (Robot.lockSystem.locked == true) {
      new LockArmExtend(Robot.lockSystem, false);
    }
    telescopeSystem.ExtendArmToPosition(set);
  }

  @Override
  public void execute() {
    if (autonMode == true) {
      double currentPos = telescopeSystem.GetArmExtendedPosition();
      if (currentPos < allowedNextCommandSet) { // Only for extend in
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
      this.telescopeSystem.ExtendArmPO(0);
    }
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
