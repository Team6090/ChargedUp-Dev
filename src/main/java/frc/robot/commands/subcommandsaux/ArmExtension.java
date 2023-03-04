package frc.robot.commands.subcommandsaux;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class ArmExtension extends CommandBase {
  IntakeSystem intakeSystem;
  double cm;
  boolean autonMode;

  double currentPos;

  boolean done = false;

  public ArmExtension(IntakeSystem intakeSystem, double cm, boolean autonMode) {
    this.intakeSystem = intakeSystem;
    this.cm = cm;
    this.autonMode = autonMode;
    cm = cm - 29;

    addRequirements(intakeSystem);
  }

  @Override
  public void initialize() {
    intakeSystem.ExtendArmToPosition(cm);
  }

  @Override
  public void execute() {
    if (autonMode == true) {
      currentPos = intakeSystem.GetArmExtendedPosition();
      if (currentPos > cm - 2 && currentPos < cm + 2) {
        done = true;
        SmartDashboard.putBoolean("ExtendMoveDone", true);
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
      this.intakeSystem.ExtendArmPO(0);
    }
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
