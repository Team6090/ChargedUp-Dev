package frc.robot.commands.subautotele.score.cones;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.intake.IntakeOpenClose;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSubsystem;

public class ScoreCN2 extends SequentialCommandGroup {

  public ScoreCN2(IntakeSystem intakeSystem, PivotSubsystem pivotSystem) {
    addCommands(
        new ArmExtension(intakeSystem, 150, true),
        new PivotMove(pivotSystem, 104.58, true),
        new ArmExtension(intakeSystem, 11506, true),
        new PivotMove(pivotSystem, 99.14, true),
        new IntakeOpenClose(intakeSystem, false),
        new ArmExtension(intakeSystem, 150, true),
        new IntakeOpenClose(intakeSystem, true),
        new PivotMove(pivotSystem, 30, true));
  }
}
