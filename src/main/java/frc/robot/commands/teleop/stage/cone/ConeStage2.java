package frc.robot.commands.teleop.stage.cone;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.ArmExtension;
import frc.robot.commands.subcommandsaux.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ConeStage2 extends SequentialCommandGroup {

  public ConeStage2(IntakeSystem intakeSystem, PivotSystem pivotSystem) { // HomePos to MidPole

    pivotSystem.SetCurrentStage(2);

    addCommands(
        new ArmExtension(intakeSystem, 150, true),
        new PivotMove(pivotSystem, 104.58, true),
        new ArmExtension(intakeSystem, 11506, true));
  }
}
