package frc.robot.commands.teleop.stage.cone;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class ConeStage3 extends SequentialCommandGroup {

  public ConeStage3(
      IntakeSystem intakeSystem,
      TelescopeSystem telescopeSystem,
      PivotSystem pivotSystem) { // HomePos to HighPole

    addCommands(
        new ArmExtension(telescopeSystem, 0, true),
        new PivotMove(pivotSystem, 115.73, true), // 113.73
        new ArmExtension(telescopeSystem, 24515, true)); // 25515
  }
}
