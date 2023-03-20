package frc.robot.commands.teleop.score.cube;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.subcommandsaux.intake.IntakeInOutAuto;
import frc.robot.commands.subcommandsaux.intake.IntakeOpenClose;
import frc.robot.commands.teleop.HomePos;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSubsystem;

public class CubeScore extends SequentialCommandGroup {

  public CubeScore(IntakeSystem intakeSystem, PivotSubsystem pivotSystem) { // At hover height
    addCommands(
        new IntakeOpenClose(intakeSystem, false),
        new IntakeInOutAuto(intakeSystem),
        new WaitCommand(0.1),
        new IntakeOpenClose(intakeSystem, true),
        new HomePos(intakeSystem, pivotSystem));
  }
}
