package frc.robot.commands.subautotele.score.cones;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.intake.IntakeOpenClose;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSubsystem;

public class ScoreCN1 extends SequentialCommandGroup {

  public ScoreCN1(IntakeSystem intakeSystem, PivotSubsystem pivotSystem) {
    addCommands(
        new ArmExtension(intakeSystem, 150, true),
        new PivotMove(pivotSystem, 60, true),
        new ArmExtension(intakeSystem, 6000, true),
        new WaitCommand(.25),
        new IntakeOpenClose(intakeSystem, false),
        new WaitCommand(.25),
        new IntakeOpenClose(intakeSystem, true),
        new ArmExtension(intakeSystem, 150, true),
        new PivotMove(pivotSystem, 30, true));
  }
}
