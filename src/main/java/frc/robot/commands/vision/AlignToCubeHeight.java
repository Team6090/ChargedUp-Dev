package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.auxiliary.PixySystem;

public class AlignToCubeHeight extends CommandBase {

  Drivetrain drivetrain;
  PixySystem pixySystem;
  double CubeX, CubeCount;
  boolean done = false;

  public AlignToCubeHeight(Drivetrain driveTrain, PixySystem pixySystem) {
    addRequirements(driveTrain);
    this.drivetrain = driveTrain;
    this.pixySystem = pixySystem;
  }

  @Override
  public void initialize() {
    drivetrain.drive(0, 0, 0);
    
    pixySystem.GetCones();
    CubeX = SmartDashboard.getNumber("Cone Height", -1);
    CubeCount = PixySystem.GetConeCount();

    if (CubeCount <= 0) {
      done = true;
    }

    if (CubeX < 195) {
      drivetrain.drive(0, -0.5, 0);
    } else if (CubeX > 205) {
      drivetrain.drive(0, 0.5, 0);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    pixySystem.GetCones();
    CubeX = SmartDashboard.getNumber("Cone Height", -1);
    CubeCount = PixySystem.GetConeCount();

    if (CubeCount == 0) {
      done = true;
      this.end(done);
    }

    if (CubeX < 205 && CubeX > 195) {
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
