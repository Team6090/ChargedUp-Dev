package frc.robot.commands.teleop.stage.cone;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.ArmExtension;
import frc.robot.commands.subcommandsaux.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ConeStage3 extends SequentialCommandGroup {

  public ConeStage3(IntakeSystem intakeSystem, PivotSystem pivotSystem) { // HomePos to HighPole

    addCommands(
        new ArmExtension(intakeSystem, 0, true),
        new PivotMove(pivotSystem, 115.73, true), // 113.73
        new ArmExtension(intakeSystem, 24515, true)); // 25515
  }
}
