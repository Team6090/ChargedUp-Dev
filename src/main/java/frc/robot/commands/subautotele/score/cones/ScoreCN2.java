package frc.robot.commands.subautotele.score.cones;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.subcommandsaux.ArmExtension;
import frc.robot.commands.subcommandsaux.IntakeOpenClose;
import frc.robot.commands.subcommandsaux.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ScoreCN2 extends SequentialCommandGroup {

  public ScoreCN2(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    addCommands(
        new ArmExtension(intakeSystem, 400, true),
        new PivotMove(pivotSystem, 104.58, true),
        new ArmExtension(intakeSystem, 11506, true),
        new PivotMove(pivotSystem, 99.14, true),
        new WaitCommand(.1),
        new IntakeOpenClose(intakeSystem, false),
        new WaitCommand(.1),
        new ArmExtension(intakeSystem, 400, true),
        new IntakeOpenClose(intakeSystem, true),
        new PivotMove(pivotSystem, 30, true));
  }
}
