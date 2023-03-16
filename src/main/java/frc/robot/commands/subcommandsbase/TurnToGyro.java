package frc.robot.commands.subcommandsbase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class TurnToGyro extends CommandBase {

  Drivetrain drivetrain;
  double gyroDegree;

  boolean done = false;

  public TurnToGyro(Drivetrain drivetrain, double gyroDegree) {
    this.drivetrain = drivetrain;
    this.gyroDegree = gyroDegree;

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    double error = gyroDegree - Math.abs(Drivetrain.gyroIO.getYaw());
    SmartDashboard.putNumber("Error", error);
    SmartDashboard.putNumber("SetValue", gyroDegree);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
    done = false;
    super.end(interrupted);
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
