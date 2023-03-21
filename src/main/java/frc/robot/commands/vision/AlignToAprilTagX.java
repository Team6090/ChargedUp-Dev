package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.limelight.Limelight;

public class AlignToAprilTagX extends CommandBase {

  Drivetrain drivetrain;
  double limelightX, limelightV;
  boolean done = false;

  Timer timer;

  int side = 0;

  public AlignToAprilTagX(Drivetrain driveTrain) {
    addRequirements(driveTrain);
    timer = new Timer();
    this.drivetrain = driveTrain;
  }

  @Override
  public void initialize() {
    timer.start();
    drivetrain.drive(0, 0, 0);

    limelightX = Limelight.GetX();
    limelightV = Limelight.GetV();

    if (limelightV == 0) {
      done = true;
    }

    if (limelightX < -1.0) {
      drivetrain.drive(0, 0.5, 0);
    } else if (limelightX > 1.0) {
      drivetrain.drive(0, -0.5, 0);
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
    limelightV = Limelight.GetV();

    if ((limelightX < 1.0 && limelightX > -1.0) || timer.get() > 5.0) {
      done = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    timer.reset();
    done = false;
    this.drivetrain.stop();
    // super.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
