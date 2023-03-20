package frc.robot.commands.subcommandsaux.util;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.PivotSubsystem;

public class PickupStationFeed extends CommandBase {

  PivotSubsystem pivotSystem;
  int set;

  boolean done;

  public PickupStationFeed(PivotSubsystem pivotSystem, int set) {
    this.pivotSystem = pivotSystem;
    this.set = set;

    addRequirements(pivotSystem);
  }

  @Override
  public void initialize() {
    pivotSystem.currentPickup = set;
    done = true;
  }

  @Override
  public void end(boolean interrupted) {
    done = false;
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
