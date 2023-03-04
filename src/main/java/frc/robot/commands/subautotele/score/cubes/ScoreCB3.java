package frc.robot.commands.subautotele.score.cubes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.subcommandsaux.ArmExtension;
import frc.robot.commands.subcommandsaux.IntakeInOut;
import frc.robot.commands.subcommandsaux.IntakeOpenClose;
import frc.robot.commands.subcommandsaux.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ScoreCB3 extends SequentialCommandGroup {

  public ScoreCB3(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    addCommands(
        new PivotMove(pivotSystem, 110, true),
        new ArmExtension(intakeSystem, 75, true),
        new IntakeInOut(intakeSystem, 1, true, true),
        new ArmExtension(intakeSystem, 0, true),
        new WaitCommand(.1),
        new IntakeOpenClose(intakeSystem, true),
        new WaitCommand(.1),
        new PivotMove(pivotSystem, 0, true));
  }
}
