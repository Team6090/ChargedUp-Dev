package frc.robot.commands.teleop.stage;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.subcommandsaux.ArmExtension;
import frc.robot.commands.subcommandsaux.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class HomePos extends CommandBase {

  IntakeSystem intakeSystem;
  PivotSystem pivotSystem;

  public HomePos(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    this.intakeSystem = intakeSystem;
    this.pivotSystem = pivotSystem;

    addRequirements(intakeSystem, pivotSystem);
  }

  @Override
  public void initialize() {
    int currentObject = intakeSystem.ObjectType();
    pivotSystem.SetCurrentStage(0);
    switch (currentObject) {
      case 0:
        Commands.sequence(
                new ArmExtension(intakeSystem, 600, true), new PivotMove(pivotSystem, 30, true))
            .schedule();
        break;

      case 1:
        Commands.sequence(
                new ArmExtension(intakeSystem, 600, true), new PivotMove(pivotSystem, 30, true))
            .schedule();
        break;

      case 2:
        Commands.sequence(
                new ArmExtension(intakeSystem, 600, true), new PivotMove(pivotSystem, 42, true))
            .schedule();
        break;

      default:
        Commands.sequence(
                new ArmExtension(intakeSystem, 600, true), new PivotMove(pivotSystem, 30, true))
            .schedule();
        break;
    }
  }
}
