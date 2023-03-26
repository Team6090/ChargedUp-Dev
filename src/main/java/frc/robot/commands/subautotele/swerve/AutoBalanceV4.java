package frc.robot.commands.subautotele.swerve;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class AutoBalanceV4 extends CommandBase {

  Drivetrain drivetrain;
  boolean reversedDirection;
  double allowedDeadbandError;
  double rateTriggerMin;

  final double initSpeed = 1.2;
  final double minActivePitch = 12.1;
  final double safeSpeed = 0.5;

  double prevPitch;
  double pitch;

  double prevTime;
  double time;

  double rateOfChange;
  double balanceSpeed;

  boolean engaged = false;

  boolean done = false;

  double lockedTime = 0.0;

  Timer timer;

  // 12.1 Down, 17.8 Up
  public AutoBalanceV4(
      Drivetrain drivetrain,
      boolean reversedDirection,
      double allowedDeadbandError,
      double rateTriggerMin) {
    this.drivetrain = drivetrain;
    this.reversedDirection = reversedDirection;
    this.allowedDeadbandError = allowedDeadbandError;
    this.rateTriggerMin = rateTriggerMin;

    timer = new Timer();

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    timer.start();
    if (reversedDirection == false) {
      drivetrain.drive(initSpeed, 0.0, 0.0);
    } else {
      drivetrain.drive(-initSpeed, 0.0, 0.0);
    }
    pitch = Drivetrain.gyroIO.getPitch();
    time = timer.get();
  }

  @Override
  public void execute() {
    prevPitch = pitch;
    prevTime = time;
    pitch = Drivetrain.gyroIO.getRoll();
    time = timer.get();

    // RateOfChange Calculation
    rateOfChange = (pitch - prevPitch) / (time - prevTime);
    // SmartDashboard.putNumber("RateOfChange(Pitch/Time)", rateOfChange);
    // SmartDashboard.putBoolean("Engaged", engaged);

    // Engage Check
    if (reversedDirection == false) {
      // SmartDashboard.putNumber("direction", 0);
      // SmartDashboard.putNumber("CCPitch", pitch);
      // SmartDashboard.putNumber("CCmin", minActivePitch);
      if (pitch < minActivePitch) {
        engaged = true;
      }
    } else {
      // SmartDashboard.putNumber("direction", 1);
      // SmartDashboard.putNumber("CCPitch", pitch);
      // SmartDashboard.putNumber("CCmin", minActivePitch);
      if (pitch > minActivePitch) {
        engaged = true;
      }
    }
    // Speed Change
    if (engaged == true) {

      if (rateOfChange > 1 || rateOfChange < -1) {
        // Safe
        balanceSpeed = Math.pow(initSpeed / Math.abs(rateOfChange), 0.225); // 0.20
      } else {
        // Bad
        balanceSpeed = safeSpeed;
      }

      // RateOfChange Check
      if (reversedDirection == false) {
        // Forward Auto Balance
        if (rateOfChange > rateTriggerMin) {
          // Climax
          // drivetrain.enableXstance();
          if (deadband(pitch, allowedDeadbandError) == 0.0) {
            // Balanced
            drivetrain.enableXstance();
            done = true;
          } else {
            // drivetrain.disableXstance();
            // Correction
            if (lockedTime == 0.0) {
              if (pitch > 0.0) {
                drivetrain.drive(0.3, 0, 0);
              } else {
                drivetrain.drive(-0.3, 0, 0);
              }
              lockedTime = timer.get();
            } else {
              if (timer.get() > lockedTime + 2) {
                drivetrain.stop();
                drivetrain.enableXstance();
                // done = true;
              }
            }
          }
        } else {
          drivetrain.drive(balanceSpeed, 0.0, 0.0);
        }

      } else {
        // Reverse Auto Balance
        if (rateOfChange < rateTriggerMin) {
          // Climax
          // drivetrain.enableXstance();
          if (deadband(pitch, allowedDeadbandError) == 0.0) {
            // Balanced
            drivetrain.enableXstance();
            done = true;
          } else {
            // Correction
            // drivetrain.disableXstance();
            if (lockedTime == 0.0) {
              if (pitch > 0.0) {
                drivetrain.drive(0.3, 0, 0);
              } else {
                drivetrain.drive(-0.3, 0, 0);
              }
              lockedTime = timer.get();
            } else {
              if (timer.get() > lockedTime + 2) {
                drivetrain.stop();
                drivetrain.enableXstance();
                // done = true;
              }
            }
          }
        } else {
          drivetrain.drive(-balanceSpeed, 0.0, 0.0);
        }
      }
    } else {
      if (timer.get() > 10) {
        drivetrain.stop();
        done = true;
      }
    }
  }

  @Override
  public void end(boolean interrupted) {
    timer.stop();
    timer.reset();
    done = false;
  }

  @Override
  public boolean isFinished() {
    return done;
  }

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
