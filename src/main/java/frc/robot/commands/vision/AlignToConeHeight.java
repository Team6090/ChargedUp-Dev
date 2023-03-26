package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.auxiliary.PixySystem;

public class AlignToConeHeight extends CommandBase {

  Drivetrain drivetrain;
  PixySystem pixySystem;
  double ConeX, ConeCount;
  boolean done = false;

  public AlignToConeHeight(Drivetrain driveTrain, PixySystem pixySystem) {
    addRequirements(driveTrain);
    this.drivetrain = driveTrain;
    this.pixySystem = pixySystem;
  }

  @Override
  public void initialize() {
    drivetrain.drive(0, 0, 0);
    
    pixySystem.GetCones();
    ConeX = SmartDashboard.getNumber("Cone Height", -1);
    ConeCount = PixySystem.GetConeCount();

    if (ConeCount <= 0) {
      done = true;
    }

    if (ConeX < 195) {
      drivetrain.drive(0, -0.5, 0);
    } else if (ConeX > 205) {
      drivetrain.drive(0, 0.5, 0);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    pixySystem.GetCones();
    ConeX = SmartDashboard.getNumber("Cone Height", -1);
    ConeCount = PixySystem.GetConeCount();

    if (ConeCount == 0) {
      done = true;
      this.end(done);
    }

    if (ConeX < 205 && ConeX > 195) {
      done = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
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
