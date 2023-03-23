package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.subcommandsaux.util.SetCurrentStage;
import frc.robot.commands.teleop.stage.cone.ConeStage1;
import frc.robot.commands.teleop.stage.cone.ConeStage2;
import frc.robot.commands.teleop.stage.cone.ConeStage3;
import frc.robot.commands.teleop.stage.cube.CubeStage1;
import frc.robot.commands.teleop.stage.cube.CubeStage2;
import frc.robot.commands.teleop.stage.cube.CubeStage3;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.PixySystem;

public class StageController extends CommandBase {

  IntakeSystem intakeSystem;
  PivotSystem pivotSystem;
  int setStageLocation;
  int currentObjectCV3;
  int currentObjectPixy;

  public StageController(IntakeSystem intakeSystem, PivotSystem pivotSystem, int setStageLocation) {
    this.intakeSystem = intakeSystem;
    this.pivotSystem = pivotSystem;
    this.setStageLocation = setStageLocation;

    addRequirements(intakeSystem, pivotSystem);
  }

  @Override
  public void initialize() {
    // int currentObject = intakeSystem.ObjectType();

    currentObjectCV3 = intakeSystem.ObjectType();
    currentObjectPixy = PixySystem.GetObject();

    int correctObject;

    if (currentObjectCV3 == currentObjectPixy) {
      correctObject = currentObjectPixy;
    } else if (currentObjectPixy == -1) {
      correctObject = currentObjectCV3;
    } else {
      correctObject = 2;
    }

    switch (correctObject) {
      case 0:
        Commands.sequence(
                new SetCurrentStage(pivotSystem, setStageLocation),
                new HomePos(intakeSystem, pivotSystem))
            .schedule();
        ;
        break;

      case 1:
        switch (setStageLocation) {
          case 0:
            Commands.sequence(
                    new SetCurrentStage(pivotSystem, setStageLocation),
                    new HomePos(intakeSystem, pivotSystem))
                .schedule();
            break;

          case 1:
            Commands.sequence(
                    new SetCurrentStage(pivotSystem, setStageLocation),
                    new ConeStage1(intakeSystem, pivotSystem))
                .schedule();
            break;

          case 2:
            Commands.sequence(
                    new SetCurrentStage(pivotSystem, setStageLocation),
                    new ConeStage2(intakeSystem, pivotSystem))
                .schedule();
            break;

          case 3:
            Commands.sequence(
                    new SetCurrentStage(pivotSystem, setStageLocation),
                    new ConeStage3(intakeSystem, pivotSystem))
                .schedule();
            break;

          default:
            Commands.sequence(
                    new SetCurrentStage(pivotSystem, setStageLocation),
                    new HomePos(intakeSystem, pivotSystem))
                .schedule();
            break;
        }
        break;

      case 2:
        switch (setStageLocation) {
          case 0:
            Commands.sequence(
                    new SetCurrentStage(pivotSystem, setStageLocation),
                    new HomePos(intakeSystem, pivotSystem))
                .schedule();
            break;

          case 1:
            Commands.sequence(
                    new SetCurrentStage(pivotSystem, setStageLocation),
                    new CubeStage1(intakeSystem, pivotSystem))
                .schedule();
            break;

          case 2:
            Commands.sequence(
                    new SetCurrentStage(pivotSystem, setStageLocation),
                    new CubeStage2(intakeSystem, pivotSystem))
                .schedule();
            break;

          case 3:
            Commands.sequence(
                    new SetCurrentStage(pivotSystem, setStageLocation),
                    new CubeStage3(intakeSystem, pivotSystem))
                .schedule();
            break;

          default:
            Commands.sequence(
                    new SetCurrentStage(pivotSystem, setStageLocation),
                    new HomePos(intakeSystem, pivotSystem))
                .schedule();
            break;
        }
        break;

      default:
        Commands.sequence(
                new SetCurrentStage(pivotSystem, setStageLocation),
                new HomePos(intakeSystem, pivotSystem))
            .schedule();
        break;
    }
  }
}
