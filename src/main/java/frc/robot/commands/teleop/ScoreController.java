package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.GetCurrentStage;
import frc.robot.commands.teleop.score.cone.ConeScore1;
import frc.robot.commands.teleop.score.cone.ConeScore2;
import frc.robot.commands.teleop.score.cone.ConeScore3;
import frc.robot.commands.teleop.score.cube.CubeScore;
import frc.robot.commands.teleop.stage.HomePos;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ScoreController extends SequentialCommandGroup {

  public ScoreController(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    // int currentStage = pivotSystem.GetCurrentStage();
    int currentObject = intakeSystem.ObjectType();
    
    GetCurrentStage getCurrentStage = new GetCurrentStage(pivotSystem);
    int currentStage = getCurrentStage.getStage();
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
