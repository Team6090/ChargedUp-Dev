package frc.robot.commands.subcommandsaux.util;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class PickupStationFeed extends CommandBase {

  PivotSystem pivotSystem;
  int set;

  boolean done;

  public PickupStationFeed(PivotSystem pivotSystem, int set) {
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
