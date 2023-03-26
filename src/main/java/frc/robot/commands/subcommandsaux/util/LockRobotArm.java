package frc.robot.commands.subcommandsaux.util;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commands.robot.LockArmExtend;
import frc.robot.commands.subcommandsaux.extension.ExtendArmO;
import frc.robot.commands.subcommandsaux.pivot.PivotArmO;
import frc.robot.commands.teleop.HomePos;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class LockRobotArm extends SequentialCommandGroup {

  public LockRobotArm(TelescopeSystem telescopeSystem, PivotSystem pivotSystem) {
    addCommands(
        new HomePos(telescopeSystem, pivotSystem), // Set To Home
        new LockArmExtend(Robot.lockSystem, true), // Lock Extend Movement
        new ExtendArmO(telescopeSystem, 0), // Release Extend Power
        new PivotArmO(pivotSystem, 0, false) // Release Pivot Power
        );
  }
}
