package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.auxiliary.PixySystem;

public class AlignToCubeHeight extends CommandBase {

  Timer timer;
  Drivetrain drivetrain;
  double CubeHeight, CubeCount;
  boolean reversed;
  boolean done = false;

  public AlignToCubeHeight(Drivetrain driveTrain, boolean reversed) {
    this.drivetrain = driveTrain;
    this.reversed = reversed;
    timer = new Timer();
    addRequirements(driveTrain);
  }

  @Override
  public void initialize() {
    drivetrain.drive(0, 0, 0);

    timer.reset();
    timer.start();
    
    PixySystem.GetCubes();
    CubeHeight = PixySystem.cubeHeight;
    CubeCount = PixySystem.GetConeCount();

    // if (CubeCount <= 0) {
    //   done = true;
    // }

    if (reversed = true){
      if (CubeHeight < 205) {
        drivetrain.drive(-0.5, 0, 0);
      }
    }else{
      if (CubeHeight < 205) {
        drivetrain.drive(0.5, 0, 0);
      }
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    PixySystem.GetCubes();
    CubeHeight = PixySystem.cubeHeight;
    CubeCount = PixySystem.GetConeCount();

    // if (CubeCount == 0) {
    //   done = true;
    //   this.end(done);
    // }

    // //if (timer.get() > 2){
    //   done = true;
    // }

    if (CubeHeight < 208 && CubeHeight > 205) {
      done = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    done = false;
    timer.stop();
    this.drivetrain.stop();
    // super.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
