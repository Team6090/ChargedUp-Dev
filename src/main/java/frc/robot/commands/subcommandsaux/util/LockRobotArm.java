package frc.robot.commands.subcommandsaux.util;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.robot.LockArmExtend;
import frc.robot.commands.subcommandsaux.extension.ExtendArmO;
import frc.robot.commands.subcommandsaux.pivot.PivotArmO;
import frc.robot.commands.teleop.HomePos;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class LockRobotArm extends SequentialCommandGroup {

  public LockRobotArm(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    addCommands(
        new HomePos(intakeSystem, pivotSystem), // Set To Home
        new LockArmExtend(Robot.lockSystem, true), // Lock Extend Movement
        new ExtendArmO(intakeSystem, 0), // Release Extend Power
        new PivotArmO(pivotSystem, 0, false) // Release Pivot Power
        );
  }
}
