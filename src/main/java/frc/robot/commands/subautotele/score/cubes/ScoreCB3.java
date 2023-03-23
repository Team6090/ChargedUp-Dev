package frc.robot.commands.subautotele.score.cubes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.intake.IntakeInOutAuto;
import frc.robot.commands.subcommandsaux.intake.IntakeOpenClose;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ScoreCB3 extends SequentialCommandGroup {

  public ScoreCB3(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    addCommands(
        new ArmExtension(intakeSystem, Constants.EXTEND_HOME_POS, true),
        new PivotMove(pivotSystem, 110, true),
        new ArmExtension(intakeSystem, 25515, true),
        new IntakeOpenClose(intakeSystem, false),
        new IntakeInOutAuto(intakeSystem),
        new ArmExtension(intakeSystem, Constants.EXTEND_HOME_POS, true),
        new WaitCommand(.1),
        new IntakeOpenClose(intakeSystem, true),
        new WaitCommand(.1),
        new PivotMove(pivotSystem, 30.5, true));
  }
}
