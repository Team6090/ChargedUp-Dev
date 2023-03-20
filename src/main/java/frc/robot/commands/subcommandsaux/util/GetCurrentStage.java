package frc.robot.commands.subcommandsaux.util;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.PivotSubsystem;

public class GetCurrentStage extends CommandBase {

  PivotSubsystem pivotSystem;

  boolean done = false;

  public GetCurrentStage(PivotSubsystem pivotSystem) {
    this.pivotSystem = pivotSystem;

    addRequirements(pivotSystem);
  }

  @Override
  public void initialize() {
    done = true;
  }

  public int getStage() {
    return pivotSystem.GetCurrentStage();
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
