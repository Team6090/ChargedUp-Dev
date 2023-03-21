package frc.robot.commands.subcommandsaux.extension;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Robot;
import frc.robot.commands.robot.LockArmExtend;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class ArmExtension extends CommandBase {
  IntakeSystem intakeSystem;
  double ec;
  boolean autonMode;

  double currentPos;

  Timer timer;

  double prevPos, newPos;
  double prevTime, newTime;

  double velo;

  boolean safe = true;

  boolean done = false;

  public ArmExtension(IntakeSystem intakeSystem, double ec, boolean autonMode) {
    this.intakeSystem = intakeSystem;
    this.ec = ec;
    this.autonMode = autonMode;

    addRequirements(intakeSystem);
  }

  @Override
  public void initialize() {
    timer.start();
    newTime = timer.get();
    newPos = intakeSystem.GetArmExtendedPosition();
    velo = intakeSystem.GetArmExtentedVelo();

    if (Robot.lockSystem.locked == true) {
      new LockArmExtend(Robot.lockSystem, false);
    }
    intakeSystem.ExtendArmToPosition(ec);
  }

  @Override
  public void execute() {
    prevTime = newTime;
    prevPos = newPos;
    newTime = timer.get();
    newPos = intakeSystem.GetArmExtendedPosition();

    double change = (newPos-prevPos)/(newTime-prevPos);

    if (velo > 1 && change < 1) {
      intakeSystem.ExtendArmToPosition(50); // Send home for reset
      done = true;
    }

    if (autonMode == true) {
      currentPos = intakeSystem.GetArmExtendedPosition();
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
        this.intakeSystem.ExtendArmPO(0);
      }
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
