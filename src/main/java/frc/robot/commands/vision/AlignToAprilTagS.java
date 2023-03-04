package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.limelight.Limelight;

public class AlignToAprilTagS extends CommandBase {

  Drivetrain drivetrain;
  double limelightS, limelightV;
  boolean done = false;

  public AlignToAprilTagS(Drivetrain driveTrain) {
    addRequirements(driveTrain);
    this.drivetrain = driveTrain;
  }

  @Override
  public void initialize() {
    drivetrain.drive(0, 0, 0);

    limelightS = Limelight.GetS();
    limelightV = Limelight.GetV();

    if (limelightV == 0) {
      done = true;
    }

    if (limelightS < 1) {
      drivetrain.drive(0, 0, .5);
    } else if (limelightS > -89) {
      drivetrain.drive(0, 0, -.5);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (limelightV == 0) {
      done = true;
    }

    limelightS = Limelight.GetS();
    limelightV = Limelight.GetV();

    while (limelightS > 1 || limelightS < -89) {

      if (limelightV == 0) {
        done = true;
      }

      limelightS = Limelight.GetS();
      limelightV = Limelight.GetV();

      if (limelightS < 1 && limelightS > -89) {
        done = true;
      }
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
