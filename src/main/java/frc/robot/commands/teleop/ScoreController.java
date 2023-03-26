package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.teleop.score.cone.ConeScore1;
import frc.robot.commands.teleop.score.cone.ConeScore2;
import frc.robot.commands.teleop.score.cone.ConeScore3;
import frc.robot.commands.teleop.score.cube.CubeScore;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.PixySystem;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class ScoreController extends CommandBase {

  IntakeSystem intakeSystem;
  TelescopeSystem telescopeSystem;
  PivotSystem pivotSystem;
  int currentStage;
  int currentObjectCV3;
  int currentObjectPixy;

  public ScoreController(
      IntakeSystem intakeSystem, TelescopeSystem telescopeSystem, PivotSystem pivotSystem) {
    this.intakeSystem = intakeSystem;
    this.telescopeSystem = telescopeSystem;
    this.pivotSystem = pivotSystem;

    addRequirements(intakeSystem, telescopeSystem, pivotSystem);
  }

  @Override
  public void initialize() {
    currentStage = pivotSystem.currentStage;
    SmartDashboard.putNumber("ReadStage", currentStage);

    currentObjectCV3 = IntakeSystem.ObjectType();
    currentObjectPixy = PixySystem.GetObject();

    int correctObject;

    if (currentObjectCV3 == currentObjectPixy) {
      correctObject = currentObjectPixy;
    } else {
      correctObject = 1;
    }

    SmartDashboard.putNumber("ReadObject", currentObjectCV3);

    switch (correctObject) {
      case 0: // No Object
        new HomePos(telescopeSystem, pivotSystem).schedule();
        break;

      case 1: // Cone
        switch (currentStage) {
          case 0:
            new HomePos(telescopeSystem, pivotSystem).schedule();
            break;

          case 1:
            new ConeScore1(intakeSystem, telescopeSystem, pivotSystem).schedule();
            break;

          case 2:
            new ConeScore2(intakeSystem, telescopeSystem, pivotSystem).schedule();
            break;

          case 3:
            new ConeScore3(intakeSystem, telescopeSystem, pivotSystem).schedule();
            break;

            // default:
            //   new HomePos(intakeSystem, pivotSystem).schedule();
            // break;
        }
        break;

      case 2: // Cube
        switch (currentStage) {
          case 0:
            new HomePos(telescopeSystem, pivotSystem).schedule();
            break;

          case 1:
            new CubeScore(intakeSystem, telescopeSystem, pivotSystem).schedule();
            break;

          case 2:
            new CubeScore(intakeSystem, telescopeSystem, pivotSystem).schedule();
            break;

          case 3:
            new CubeScore(intakeSystem, telescopeSystem, pivotSystem).schedule();
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
