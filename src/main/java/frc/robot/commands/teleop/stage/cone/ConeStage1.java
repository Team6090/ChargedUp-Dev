package frc.robot.commands.teleop.stage.cone;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.ArmExtension;
import frc.robot.commands.subcommandsaux.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ConeStage1 extends SequentialCommandGroup {

  public ConeStage1(IntakeSystem intakeSystem, PivotSystem pivotSystem) { // HomePos to Ground

    pivotSystem.currentStage = 1;

    addCommands(
        new ArmExtension(intakeSystem, 0, true),
        new PivotMove(pivotSystem, 60, true),
        new ArmExtension(intakeSystem, 6000, true));
  }
}
