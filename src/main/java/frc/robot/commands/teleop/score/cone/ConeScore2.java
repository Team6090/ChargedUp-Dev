package frc.robot.commands.teleop.score.cone;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.subcommandsaux.ArmExtension;
import frc.robot.commands.subcommandsaux.IntakeOpenClose;
import frc.robot.commands.subcommandsaux.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ConeScore2 extends SequentialCommandGroup {

  public ConeScore2(IntakeSystem intakeSystem, PivotSystem pivotSystem) { // At hover height
    addCommands(
        new ArmExtension(intakeSystem, 11506, true), // FIXME: Remove if arm can hold
        new PivotMove(pivotSystem, 99.14, true),
        new WaitCommand(.1),
        new IntakeOpenClose(intakeSystem, false),
        new WaitCommand(.1),
        new ArmExtension(intakeSystem, 150, true),
        new IntakeOpenClose(intakeSystem, true),
        new PivotMove(pivotSystem, 30, true));
  }
}
