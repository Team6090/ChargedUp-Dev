package frc.robot.commands.teleop.stage.cone;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class ConeStage3Auto extends SequentialCommandGroup {

  public ConeStage3Auto(
      IntakeSystem intakeSystem,
      TelescopeSystem telescopeSystem,
      PivotSystem pivotSystem) { // HomePos to Ground

    addCommands(
        new ArmExtension(telescopeSystem, 0, true),
        new PivotMove(pivotSystem, 60, true),
        new ArmExtension(telescopeSystem, 6000, true));
  }
}
