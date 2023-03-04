package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.limelight.Limelight;

public class AlignToAprilTagX extends CommandBase {

  Drivetrain drivetrain;
  double limelightX, limelightY, limelightV;
  boolean done = false;

  int side = 0;

  public AlignToAprilTagX(Drivetrain driveTrain) {
    addRequirements(driveTrain);
    this.drivetrain = driveTrain;
  }

  @Override
  public void initialize() {
    drivetrain.drive(0, 0, 0);

    limelightX = Limelight.GetX();
    limelightY = Limelight.GetY();
    limelightV = Limelight.GetV();

    if (limelightV == 0) {
      done = true;
    }

    if (limelightX < -1) {
      side = -1;
      drivetrain.drive(0, 1.0, 0);
    } else if (limelightX > 1) {
      drivetrain.drive(0, -1.0, 0);
      side = 1;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (limelightV == 0) {
      done = true;
      this.end(done);
    }

    limelightX = Limelight.GetX();
    limelightY = Limelight.GetY();
    limelightV = Limelight.GetV();

    if (limelightX < 1 && side == 1) {
      this.drivetrain.stop();
      done = true;
      this.end(done);
    }
    if (limelightX < 1 && side == -1) {
      this.drivetrain.stop();
      done = true;
      this.end(done);
    }
    if (limelightX > 1 && side == -1) {
      this.drivetrain.stop();
      done = true;
      this.end(done);
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
