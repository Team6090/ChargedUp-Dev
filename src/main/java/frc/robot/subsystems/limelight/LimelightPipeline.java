package frc.robot.subsystems.limelight;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class LimelightPipeline extends CommandBase {

  int pipeline;
  boolean done;

  public LimelightPipeline(int pipeline) {
    this.pipeline = pipeline;
  }

  @Override
  public void initialize() {
    Limelight.SetPipeline(pipeline);
    done = true;
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return done;
  }
}
