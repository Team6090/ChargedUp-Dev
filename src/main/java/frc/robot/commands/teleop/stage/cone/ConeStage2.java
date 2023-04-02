package frc.robot.commands.teleop.stage.cone;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class ConeStage2 extends SequentialCommandGroup {

  public ConeStage2(
      IntakeSystem intakeSystem,
      TelescopeSystem telescopeSystem,
      PivotSystem pivotSystem) { // HomePos to MidPole

    addCommands(
        new ArmExtension(telescopeSystem, 0, true),
        new PivotMove(pivotSystem, 103.58, true),//105.58
        new ArmExtension(telescopeSystem, 10506, true));
  }
}
