package frc.robot.commands.teleop.score.cone;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.intake.IntakeOpenClose;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ConeScore1 extends SequentialCommandGroup {

  public ConeScore1(IntakeSystem intakeSystem, PivotSystem pivotSystem) { // At hover height
    addCommands(
        // new ArmExtension(intakeSystem, 6000, true), // FIXME: Remove if arm can hold
        new PivotMove(pivotSystem, 60, true),
        new WaitCommand(.1),
        new IntakeOpenClose(intakeSystem, false),
        new WaitCommand(.1),
        new ArmExtension(intakeSystem, 0, true),
        new IntakeOpenClose(intakeSystem, true),
        new PivotMove(pivotSystem, 30, true));
  }
}
