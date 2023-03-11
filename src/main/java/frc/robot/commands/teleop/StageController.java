package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.SetCurrentStage;
import frc.robot.commands.teleop.stage.HomePos;
import frc.robot.commands.teleop.stage.cone.ConeStage1;
import frc.robot.commands.teleop.stage.cone.ConeStage2;
import frc.robot.commands.teleop.stage.cone.ConeStage3;
import frc.robot.commands.teleop.stage.cube.CubeStage1;
import frc.robot.commands.teleop.stage.cube.CubeStage2;
import frc.robot.commands.teleop.stage.cube.CubeStage3;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class StageController extends CommandBase {

  IntakeSystem intakeSystem;
  PivotSystem pivotSystem;
  int setStageLocation;

  public StageController(IntakeSystem intakeSystem, PivotSystem pivotSystem, int setStageLocation) {
    this.intakeSystem = intakeSystem;
    this.pivotSystem = pivotSystem;
    this.setStageLocation = setStageLocation;

    addRequirements(intakeSystem, pivotSystem);
  }

  @Override
  public void initialize() {
    int currentObject = intakeSystem.ObjectType();

    switch (currentObject) {
      case 0:
        Commands.sequence(
            new HomePos(intakeSystem, pivotSystem),
            new SetCurrentStage(pivotSystem, setStageLocation)).schedule();;
        break;

      case 1:
        switch (setStageLocation) {
          case 0:
            Commands.sequence(
                new HomePos(intakeSystem, pivotSystem),
                new SetCurrentStage(pivotSystem, setStageLocation)).schedule();
            break;

          case 1:
            Commands.sequence(
                new ConeStage1(intakeSystem, pivotSystem),
                new SetCurrentStage(pivotSystem, setStageLocation)).schedule();
            break;

          case 2:
            Commands.sequence(
                new ConeStage2(intakeSystem, pivotSystem),
                new SetCurrentStage(pivotSystem, setStageLocation)).schedule();
            break;

          case 3:
            Commands.sequence(
                new ConeStage3(intakeSystem, pivotSystem),
                new SetCurrentStage(pivotSystem, setStageLocation)).schedule();
            break;

          default:
            Commands.sequence(
                new HomePos(intakeSystem, pivotSystem),
                new SetCurrentStage(pivotSystem, setStageLocation)).schedule();
            break;
        }
        break;

      case 2:
        switch (setStageLocation) {
          case 0:
          Commands.sequence(
                new HomePos(intakeSystem, pivotSystem),
                new SetCurrentStage(pivotSystem, setStageLocation)).schedule();
            break;

          case 1:
          Commands.sequence(
                new CubeStage1(intakeSystem, pivotSystem),
                new SetCurrentStage(pivotSystem, setStageLocation)).schedule();
            break;

          case 2:
          Commands.sequence(
                new CubeStage2(intakeSystem, pivotSystem),
                new SetCurrentStage(pivotSystem, setStageLocation)).schedule();
            break;

          case 3:
          Commands.sequence(
                new CubeStage3(intakeSystem, pivotSystem),
                new SetCurrentStage(pivotSystem, setStageLocation)).schedule();
            break;

          default:
          Commands.sequence(
                new HomePos(intakeSystem, pivotSystem),
                new SetCurrentStage(pivotSystem, setStageLocation)).schedule();
            break;
        }
        break;

      default:
      Commands.sequence(
            new HomePos(intakeSystem, pivotSystem),
            new SetCurrentStage(pivotSystem, setStageLocation)).schedule();
        break;
    }
  }
}
