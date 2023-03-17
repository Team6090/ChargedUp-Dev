package frc.robot.commands.subcommandsaux.util;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.PivotSubsystem;

public class SetCurrentStage extends CommandBase {

  PivotSubsystem pivotSystem;
  int setStage;

  boolean done = false;

  public SetCurrentStage(PivotSubsystem pivotSystem, int setStage) {
    this.setStage = setStage;
    this.pivotSystem = pivotSystem;

    addRequirements(pivotSystem);
  }

  @Override
  public void initialize() {
    pivotSystem.SetCurrentStage(setStage);
    done = true;
  }

  @Override
  public void end(boolean interrupted) {
    done = false;
    super.end(interrupted);
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
