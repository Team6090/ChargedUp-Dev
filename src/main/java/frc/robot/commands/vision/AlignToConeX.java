package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.auxiliary.PixySystem;

public class AlignToConeX extends CommandBase {

  Timer timer;
  Drivetrain drivetrain;
  double ConeX, ConeCount;
  boolean reversed;
  boolean done = false;

  public AlignToConeX(Drivetrain driveTrain, boolean reversed) {
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
    
    PixySystem.GetCones();
    ConeX = PixySystem.coneX;
    ConeCount = PixySystem.GetConeCount();

    if (ConeCount <= 0) {
      done = true;
    }

    if (reversed = true){
      if (ConeX < 180) {
        drivetrain.drive(0, 0.5, 0);
      } else if (ConeX > 200) {
        drivetrain.drive(0, -0.5, 0);
      }
    }else{
      if (ConeX < 180) {
        drivetrain.drive(0, -0.5, 0);
      } else if (ConeX > 200) {
        drivetrain.drive(0, 0.5, 0);
      }
    }
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    PixySystem.GetCones();
    ConeX = PixySystem.coneX;
    ConeCount = PixySystem.GetConeCount();

    if (ConeCount == 0) {
      done = true;
      this.end(done);
    }

    if (ConeX < 200 && ConeX > 180) {
      done = true;
    }

    if(timer.get() > 2){
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
