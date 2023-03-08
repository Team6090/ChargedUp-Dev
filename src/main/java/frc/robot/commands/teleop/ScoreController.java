package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.teleop.score.cone.ConeScore1;
import frc.robot.commands.teleop.score.cone.ConeScore2;
import frc.robot.commands.teleop.score.cone.ConeScore3;
import frc.robot.commands.teleop.score.cube.CubeScore;
import frc.robot.commands.teleop.stage.HomePos;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ScoreController extends SequentialCommandGroup {

  public ScoreController(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    int currentStage = pivotSystem.currentStage;
    int currentObject = intakeSystem.ObjectType();
    SmartDashboard.putNumber("Stage", currentStage);

    switch (currentObject) {
      case 0:
        addCommands(new HomePos(intakeSystem, pivotSystem));
        break;

      case 1:
        switch (currentStage) { // Cone
          case 0:
            addCommands(new HomePos(intakeSystem, pivotSystem));
            break;

          case 1:
            addCommands(new ConeScore1(intakeSystem, pivotSystem));
            break;

          case 2:
            addCommands(new ConeScore2(intakeSystem, pivotSystem));
            break;

          case 3:
            addCommands(new ConeScore3(intakeSystem, pivotSystem));
            break;

          default:
            addCommands(new HomePos(intakeSystem, pivotSystem));
        }
        break;

      case 2:
        switch (currentStage) {
          case 0:
            addCommands(new HomePos(intakeSystem, pivotSystem));
            break;

          case 1:
            addCommands(new CubeScore(intakeSystem, pivotSystem));
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
