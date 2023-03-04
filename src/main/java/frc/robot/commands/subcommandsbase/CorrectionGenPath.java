package frc.robot.commands.subcommandsbase;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.pathplanner.FollowPath;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.drivetrain.DrivetrainConstants;
import frc.robot.subsystems.limelight.Limelight;

public class CorrectionGenPath extends CommandBase {

  private Drivetrain drivetrain;

  private double posX;
  private double posY;

  private double idealX;
  private double idealY;
  private Rotation2d idealHeading;

  private FollowPath command;

  private boolean done;

  public CorrectionGenPath(
      Drivetrain drivetrain, double idealX, double idealY, Rotation2d idealHeading) {
    this.drivetrain = drivetrain;
    this.idealX = idealX;
    this.idealY = idealY;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    posX = Limelight.PositionOnFieldGrid().getX();
    posY = Limelight.PositionOnFieldGrid().getY();
    if (posX == 0.0 || posY == 0.0) {
      done = true;
    }
    PathPlannerTrajectory path =
        PathPlanner.generatePath(
            new PathConstraints(
                DrivetrainConstants.AUTO_MAX_SPEED_METERS_PER_SECOND,
                DrivetrainConstants.AUTO_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED),
            new PathPoint(new Translation2d(posX, posY), drivetrain.getGyroscopeRotation()),
            new PathPoint(new Translation2d(idealX, idealY), idealHeading));
    command = new FollowPath(path, drivetrain, false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (command.isFinished() == true) {
      done = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.drivetrain.stop();
    super.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
