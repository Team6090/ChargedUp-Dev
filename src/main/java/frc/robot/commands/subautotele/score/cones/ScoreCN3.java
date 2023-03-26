package frc.robot.commands.subautotele.score.cones;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.intake.IntakeOpenClose;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class ScoreCN3 extends SequentialCommandGroup {

  public ScoreCN3(
      IntakeSystem intakeSystem, TelescopeSystem telescopeSystem, PivotSystem pivotSystem) {
    addCommands(
        new ArmExtension(telescopeSystem, Constants.EXTEND_HOME_POS, true),
        new PivotMove(pivotSystem, 113.73, true),
        new ArmExtension(telescopeSystem, 25515, true),
        new PivotMove(pivotSystem, 107.66, true),
        new WaitCommand(.1),
        new IntakeOpenClose(intakeSystem, false),
        new WaitCommand(.1),
        new ArmExtension(telescopeSystem, Constants.EXTEND_HOME_POS, true),
        new IntakeOpenClose(intakeSystem, true),
        new PivotMove(pivotSystem, 30.5, true));
  }
}
