package frc.robot.commands.subcommandsaux.extension;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class ArmExtension extends CommandBase {
  IntakeSystem intakeSystem;
  double ec;
  boolean autonMode;

  double currentPos;

  boolean done = false;

  public ArmExtension(IntakeSystem intakeSystem, double ec, boolean autonMode) {
    this.intakeSystem = intakeSystem;
    this.ec = ec;
    this.autonMode = autonMode;

    addRequirements(intakeSystem);
  }

  @Override
  public void initialize() {
    intakeSystem.ExtendArmToPosition(ec);
  }

  @Override
  public void execute() {
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
