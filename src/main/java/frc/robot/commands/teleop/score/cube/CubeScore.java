package frc.robot.commands.teleop.score.cube;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.IntakeInOut;
import frc.robot.commands.teleop.stage.HomePos;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class CubeScore extends SequentialCommandGroup {

  public CubeScore(IntakeSystem intakeSystem, PivotSystem pivotSystem) { // At hover height
    addCommands(
        new IntakeInOut(intakeSystem, .75, false, true),
        new HomePos(intakeSystem, pivotSystem)
    );
  }
}
