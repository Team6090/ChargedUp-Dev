package frc.robot.commands.subautotele.score.cubes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.subcommandsaux.ArmExtension;
import frc.robot.commands.subcommandsaux.IntakeInOut;
import frc.robot.commands.subcommandsaux.IntakeOpenClose;
import frc.robot.commands.subcommandsaux.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ScoreCB2 extends SequentialCommandGroup {

  public ScoreCB2(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    addCommands(
        new ArmExtension(intakeSystem, 150, true),
        new PivotMove(pivotSystem, 90, true),
        new ArmExtension(intakeSystem, 11000, true),
        new IntakeOpenClose(intakeSystem, false),
        new ArmExtension(intakeSystem, 150, true),
        new WaitCommand(.1),
        new IntakeOpenClose(intakeSystem, true),
        new WaitCommand(.1),
        new PivotMove(pivotSystem, 30.5, true));
  }
}
