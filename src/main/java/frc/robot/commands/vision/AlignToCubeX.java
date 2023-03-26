package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.auxiliary.PixySystem;

public class AlignToCubeX extends CommandBase {

  Drivetrain drivetrain;
  PixySystem pixySystem;
  double CubeX, CubeCount;
  boolean done = false;

  public AlignToCubeX(Drivetrain driveTrain, PixySystem pixySystem) {
    addRequirements(driveTrain);
    this.drivetrain = driveTrain;
    this.pixySystem = pixySystem;
  }

  @Override
  public void initialize() {
    drivetrain.drive(0, 0, 0);
    
    pixySystem.GetCubes();
    CubeX = SmartDashboard.getNumber("Cube X", -1);
    CubeCount = PixySystem.GetCubeCount();

    if (CubeCount <= 0) {
      done = true;
    }

    if (CubeX < 155) {
      drivetrain.drive(0, -0.5, 0);
    } else if (CubeX > 165) {
      drivetrain.drive(0, 0.5, 0);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    pixySystem.GetCones();
    CubeX = SmartDashboard.getNumber("Cone X", -1);
    CubeCount = PixySystem.GetConeCount();

    if (CubeCount == 0) {
      done = true;
      this.end(done);
    }

    if (CubeX < 165 && CubeX > 155) {
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
