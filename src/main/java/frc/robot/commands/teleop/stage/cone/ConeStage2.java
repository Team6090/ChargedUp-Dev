package frc.robot.commands.teleop.stage.cone;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSubsystem;

public class ConeStage2 extends SequentialCommandGroup {

  public ConeStage2(IntakeSystem intakeSystem, PivotSubsystem pivotSystem) { // HomePos to MidPole

    addCommands(
        new ArmExtension(intakeSystem, 0, true),
        new PivotMove(pivotSystem, 105.58, true),
        new ArmExtension(intakeSystem, 10506, true));
  }
}
