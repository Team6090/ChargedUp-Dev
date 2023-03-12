package frc.robot.commands.subautotele.swerve;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class AutoBalanceV3 extends CommandBase {
    
    Drivetrain drivetrain;
    boolean reversed;
    double allowedError;
    double activateMin;
    double changeMin;

    double gyroPitch;

    double prevPitch, newPitch;
    double prevTime, newTime;
    int setDriveDirection;

    boolean engagedWithStation = false;
    boolean pastClimax = false;

    final double speed = 0.5;
    final double minSpeed = 0.4;

    boolean done = false;

    Timer timer;

    public AutoBalanceV3(Drivetrain drivetrain, boolean reversed, double allowedError, double activateMin, double changeMin) {
        this.drivetrain = drivetrain;
        this.reversed = reversed;
        this.allowedError = allowedError;
        this.activateMin = activateMin;
        this.changeMin = changeMin;

        timer = new Timer();

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        timer.start();
        gyroPitch = Drivetrain.gyroIO.getPitch();
        if (deadband(gyroPitch, allowedError) > activateMin) {
            // engagedWithStation = true;
        }
        if (reversed == false) { // Only when run in auton group
            drivetrain.drive(speed, 0, 0);
        } else {
            drivetrain.drive(-speed, 0, 0);
        }
        newPitch = gyroPitch;
        newTime = timer.get();
    }

    @Override
    public void execute() {
        SmartDashboard.putBoolean("Climax", pastClimax);
        SmartDashboard.putBoolean("EnagagedWithSubStation", engagedWithStation);
        gyroPitch = Drivetrain.gyroIO.getPitch();
        if (pastClimax = false) {

            if (engagedWithStation == true) {
                if (deadband(gyroPitch, allowedError) == 0.0) {
                    // done = true;
                }else {
                    // Updates
                    prevPitch = newPitch;
                    newPitch = gyroPitch;
                    prevTime = newTime;
                    newTime = timer.get();
                    
                    double changeOverTime = (newPitch-prevPitch)/(newTime-prevTime);
                    SmartDashboard.putNumber("ChangeOverTime", changeOverTime);
                    
                    if (gyroPitch > 0) {
                        setDriveDirection = -1;
                    }else if (gyroPitch < 0) {  
                        setDriveDirection = 1;
                    }
                    SmartDashboard.putNumber("Judge", judgeSpeed(speed/Math.abs(changeOverTime)*setDriveDirection));
                    if (changeOverTime < changeMin) { // Change for forward / backward
                    pastClimax = true;
                    // drivetrain.enableXstance();
                    // engagedWithStation = false;
                    // Fast Change (Breaking past mid)
                    // drivetrain.drive(judgeSpeed(speed/Math.abs(changeOverTime)*setDriveDirection), 0, 0); // Calculated Speed
                } else {
                    // Slow Change (Remain Constant?)
                    if (changeOverTime <= changeMin && deadband(gyroPitch, allowedError) == 0.0) {
                        done = true;
                    }
                }
                
            }
        } else if (pastClimax == true) {
                    if(deadband(gyroPitch, allowedError) == 0.0) {
                        done = true;
                        super.end(done);
                    }
        }
        } else {
            if (deadband(gyroPitch, allowedError) < activateMin) {
                engagedWithStation = true;
            } 
        }
    }
    
    @Override
    public void end(boolean interrupted) {
        done = true;
        timer.stop();
        timer.reset();
        super.end(interrupted);
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

    private double judgeSpeed(double judgeSpeedVal) {
        if (judgeSpeedVal > minSpeed) {
            return judgeSpeedVal;
        } else {
            return minSpeed;
        }
    }


}