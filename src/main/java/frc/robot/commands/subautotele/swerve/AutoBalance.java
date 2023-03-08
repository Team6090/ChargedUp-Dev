package frc.robot.commands.subautotele.swerve;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class AutoBalance extends CommandBase {

  Drivetrain drivetrain;

  double pitch;

  boolean engaged = false;

  SwerveModulePosition[] swerveModulePositions;

  public AutoBalance(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;

    addRequirements(drivetrain);
  }

  @Override // TODO: Run Sample Auton or SubAutoTele with and without @Override to see if it holds
  // values
  public void initialize() {
    pitch = drivetrain.getPitch();

    swerveModulePositions = drivetrain.swerveModulePositions;

    drivetrain.drive(.5, 0, 0); // TODO: Scale drive on pitch of robot to get perfect balance
  }

  @Override
  public void execute() {
    if (engaged == false) {
      if (Math.abs(deadband(pitch, 0.1)) > 0) {
        engaged = true;
      }
    } else {
      if (Math.abs(deadband(pitch, 0.1)) == 0.0) { // TODO: Correction on overshoot
        drivetrain.stop();
        super.end(false);
      }
    }
  }

  @Override
  public void end(boolean interrupted) {}

  private double deadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }
}
