package frc.robot.commands.subcommandsaux.extension;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.commands.robot.LockArmExtend;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class ArmExtension extends CommandBase {
  TelescopeSystem telescopeSystem;
  double ec;
  boolean autonMode;

  double currentPos;

  boolean done = false;

  public ArmExtension(TelescopeSystem telescopeSystem, double ec, boolean autonMode) {
    this.telescopeSystem = telescopeSystem;
    this.ec = ec;
    this.autonMode = autonMode;
    // telescopeSystem.armRetractMotor.configMotionAcceleration(10000, 10);

    addRequirements(telescopeSystem);
  }

  @Override
  public void initialize() {
    if (Robot.lockSystem.locked == true) {
      new LockArmExtend(Robot.lockSystem, false);
    }
    telescopeSystem.ExtendArmToPosition(ec);
  }

  @Override
  public void execute() {
    if (autonMode == true) {
      currentPos = telescopeSystem.GetArmExtendedPosition();
      if (Math.abs(telescopeSystem.collection.getIntegratedSensorVelocity()) > 100 && Math.abs(telescopeSystem.armRetractMotor.getSelectedSensorVelocity()) < 100 ) {
        done = true;
      }
      if (currentPos > ec - 100 && currentPos < ec + 100) {
        done = true;
        SmartDashboard.putBoolean("ExtendMoveDone", true);
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
