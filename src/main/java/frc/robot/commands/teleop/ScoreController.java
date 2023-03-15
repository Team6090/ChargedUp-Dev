package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.teleop.score.cone.ConeScore1;
import frc.robot.commands.teleop.score.cone.ConeScore2;
import frc.robot.commands.teleop.score.cone.ConeScore3;
import frc.robot.commands.teleop.score.cube.CubeScore;
import frc.robot.commands.teleop.stage.HomePos;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ScoreController extends CommandBase {

  IntakeSystem intakeSystem;
  PivotSystem pivotSystem;
  int currentStage;
  int currentObject;

  public ScoreController(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    this.intakeSystem = intakeSystem;
    this.pivotSystem = pivotSystem;

    addRequirements(intakeSystem, pivotSystem);
  }

  @Override
  public void initialize() {
    currentStage = pivotSystem.currentStage;
    SmartDashboard.putNumber("ReadStage", currentStage);

    currentObject = intakeSystem.ObjectType();
    SmartDashboard.putNumber("ReadObject", currentObject);

    switch (currentObject) {
      case 0: // No Object
        new HomePos(intakeSystem, pivotSystem).schedule();
        break;

      case 1: // Cone
        switch (currentStage) {
          case 0:
            new HomePos(intakeSystem, pivotSystem).schedule();
            break;

          case 1:
            new ConeScore1(intakeSystem, pivotSystem).schedule();
            break;

          case 2:
            new ConeScore2(intakeSystem, pivotSystem).schedule();
            break;

          case 3:
            new ConeScore3(intakeSystem, pivotSystem).schedule();
            break;

            // default:
            //   new HomePos(intakeSystem, pivotSystem).schedule();
            // break;
        }
        break;

      case 2: // Cube
        switch (currentStage) {
          case 0:
            new HomePos(intakeSystem, pivotSystem).schedule();
            break;

          case 1:
            new CubeScore(intakeSystem, pivotSystem).schedule();
            break;

          case 2:
            new CubeScore(intakeSystem, pivotSystem).schedule();
            break;

          case 3:
            new CubeScore(intakeSystem, pivotSystem).schedule();
            break;

            // default:
            //   new HomePos(intakeSystem, pivotSystem).schedule();
            // break;
        }
        break;

        // default:
        //   new HomePos(intakeSystem, pivotSystem).schedule();
        //   break;
    }
  }
}
