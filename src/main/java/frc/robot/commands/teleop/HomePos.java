package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.commands.subcommandsaux.util.PickupStationFeed;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSubsystem;

public class HomePos extends CommandBase {

  IntakeSystem intakeSystem;
  PivotSubsystem pivotSystem;
  int currentStage;
  int pickupStation;

  public HomePos(IntakeSystem intakeSystem, PivotSubsystem pivotSystem) {
    this.intakeSystem = intakeSystem;
    this.pivotSystem = pivotSystem;

    addRequirements(intakeSystem, pivotSystem);
  }

  @Override
  public void initialize() {
    currentStage = pivotSystem.currentStage;
    int currentObject = intakeSystem.ObjectType();
    pickupStation = pivotSystem.currentPickup;
    // TODO: StationPickup to home (up before retract)

    if (pickupStation
        == 1) { // Cleanup add more safety for command clashing (interruping other commands)
      Commands.sequence(
              new PickupStationFeed(pivotSystem, 0),
              new ArmExtension(intakeSystem, 50, true),
              new PivotMove(pivotSystem, 30, true))
          .schedule();
    } else if (pickupStation == 2) {
      Commands.sequence(
              new PickupStationFeed(pivotSystem, 0),
              new PivotMove(pivotSystem, 317.0, true),
              new ArmExtension(intakeSystem, 50, true),
              new PivotMove(pivotSystem, 30, true))
          .schedule();
    } else if (pickupStation == 3) {
      Commands.sequence(
              new PickupStationFeed(pivotSystem, 0),
              new PivotMove(pivotSystem, 118.0, true),
              new ArmExtension(intakeSystem, 50, true),
              new PivotMove(pivotSystem, 30, true))
          .schedule();
    } else {
      new PickupStationFeed(pivotSystem, 0).schedule(); // Set current pickup to home
      switch (currentObject) {
        case 0:
          Commands.sequence(
                  new ArmExtension(intakeSystem, 50, true), new PivotMove(pivotSystem, 30, true))
              .schedule();
          break;

        case 1:
          Commands.sequence(
                  new ArmExtension(intakeSystem, 50, true), new PivotMove(pivotSystem, 30, true))
              .schedule();
          break;

        case 2:
          Commands.sequence(
                  new ArmExtension(intakeSystem, 50, true), new PivotMove(pivotSystem, 42, true))
              .schedule();
          break;

        default:
          Commands.sequence(
                  new ArmExtension(intakeSystem, 50, true), new PivotMove(pivotSystem, 30, true))
              .schedule();
          break;
      }
    }
    pivotSystem.SetCurrentStage(0);
  }
}
