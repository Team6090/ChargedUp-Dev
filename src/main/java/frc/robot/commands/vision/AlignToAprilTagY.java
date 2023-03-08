package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.limelight.Limelight;

public class AlignToAprilTagY extends CommandBase {

  Drivetrain drivetrain;
  double limelightY, limelightV;
  boolean done = false;

  public AlignToAprilTagY(Drivetrain driveTrain) {
    addRequirements(driveTrain);
    this.drivetrain = driveTrain;
  }

  @Override
  public void initialize() {
    drivetrain.drive(0, 0, 0);

    limelightY = Limelight.GetY();
    limelightV = Limelight.GetV();

    if (limelightV == 0) {
      done = true;
    }

    if (limelightY < -1.0) {
      drivetrain.drive(-0.5, 0, 0);
    } else if (limelightY > 1.0) {
      drivetrain.drive(0.5, 0, 0);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (limelightV == 0) {
      done = true;
    }

    limelightY = Limelight.GetY();
    limelightV = Limelight.GetV();

      if (limelightY < 1.0 && limelightY > -1.0) {
        done = true;
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    done = false;
    this.drivetrain.stop();
    super.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
