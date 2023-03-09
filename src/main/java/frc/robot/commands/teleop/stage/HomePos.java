package frc.robot.commands.teleop.stage;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.ArmExtension;
import frc.robot.commands.subcommandsaux.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class HomePos extends SequentialCommandGroup {

  public HomePos(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    int currentObject = intakeSystem.ObjectType();
    pivotSystem.SetCurrentStage(0);

    switch (currentObject) {
      case 0:
        addCommands(
            new ArmExtension(intakeSystem, 150, true), new PivotMove(pivotSystem, 30, true));
        break;

      case 1:
        addCommands(
            new ArmExtension(intakeSystem, 150, true), new PivotMove(pivotSystem, 30, true));
        break;

      case 2:
        addCommands(
            new ArmExtension(intakeSystem, 587, true), new PivotMove(pivotSystem, 39.375, true));
        break;

      default:
        addCommands(
            new ArmExtension(intakeSystem, 150, true), new PivotMove(pivotSystem, 30, true));
    }

    addCommands(new ArmExtension(intakeSystem, 150, true), new PivotMove(pivotSystem, 30, true));
  }
}
