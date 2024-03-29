package frc.robot.commands.teleop.stage.cube;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class CubeStage1 extends SequentialCommandGroup {

  public CubeStage1(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    addCommands(
        new ArmExtension(intakeSystem, 0, true),
        new PivotMove(pivotSystem, 60, true),
        new ArmExtension(intakeSystem, 6000, true));
  }
}
