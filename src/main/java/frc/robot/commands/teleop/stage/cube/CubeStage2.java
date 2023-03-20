package frc.robot.commands.teleop.stage.cube;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSubsystem;

public class CubeStage2 extends SequentialCommandGroup {

  public CubeStage2(IntakeSystem intakeSystem, PivotSubsystem pivotSystem) {
    addCommands(
        new ArmExtension(intakeSystem, 0, true),
        new PivotMove(pivotSystem, 88.85, true),
        new ArmExtension(intakeSystem, 7747, true));
  }
}
