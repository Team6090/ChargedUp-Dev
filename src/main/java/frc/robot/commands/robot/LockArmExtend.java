package frc.robot.commands.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.LockSystem;

public class LockArmExtend extends CommandBase {

  LockSystem lockSystem;
  boolean enabled;
  boolean done = false;

  public LockArmExtend(LockSystem lockSystem, boolean enabled) {
    this.lockSystem = lockSystem;
    this.enabled = enabled;

    addRequirements(lockSystem);
  }

  @Override
  public void initialize() {
    lockSystem.extendLock(enabled);
    done = true;
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    done = false;
    super.end(interrupted);
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
