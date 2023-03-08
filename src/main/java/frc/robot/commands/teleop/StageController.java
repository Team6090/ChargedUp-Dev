package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.teleop.stage.HomePos;
import frc.robot.commands.teleop.stage.cone.ConeStage1;
import frc.robot.commands.teleop.stage.cone.ConeStage2;
import frc.robot.commands.teleop.stage.cone.ConeStage3;
import frc.robot.commands.teleop.stage.cube.CubeStage1;
import frc.robot.commands.teleop.stage.cube.CubeStage2;
import frc.robot.commands.teleop.stage.cube.CubeStage3;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class StageController extends SequentialCommandGroup {

  public StageController(IntakeSystem intakeSystem, PivotSystem pivotSystem, int setStageLocation) {
    int currentObject = intakeSystem.ObjectType();

    SmartDashboard.putNumber("CurrentObj", currentObject);

    switch (currentObject) {
      case 0:
        addCommands(new HomePos(intakeSystem, pivotSystem));
        break;

      case 1:
        switch (setStageLocation) {
          case 0:
            addCommands(new HomePos(intakeSystem, pivotSystem));
            break;

          case 1:
            addCommands(new ConeStage1(intakeSystem, pivotSystem));
            break;

          case 2:
            addCommands(new ConeStage2(intakeSystem, pivotSystem));
            break;

          case 3:
            addCommands(new ConeStage3(intakeSystem, pivotSystem));
            break;

          default:
            addCommands(new HomePos(intakeSystem, pivotSystem));
        }
        break;

      case 2:
        switch (setStageLocation) {
          case 0:
            addCommands(new HomePos(intakeSystem, pivotSystem));
            break;

          case 1:
            addCommands(new CubeStage1(intakeSystem, pivotSystem));
            break;

          case 2:
            addCommands(new CubeStage2(intakeSystem, pivotSystem));
            break;

          case 3:
            addCommands(new CubeStage3(intakeSystem, pivotSystem));
            break;

          default:
            addCommands(new HomePos(intakeSystem, pivotSystem));
        }
        break;

      default:
        addCommands(new HomePos(intakeSystem, pivotSystem));
    }
  }
}
