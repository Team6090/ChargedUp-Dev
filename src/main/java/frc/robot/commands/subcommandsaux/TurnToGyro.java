package frc.robot.commands.subcommandsaux;

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
    public void execute() {
        double error = gyroDegree - Drivetrain.gyroIO.getYaw();
        SmartDashboard.putNumber("Error", error);
        SmartDashboard.putNumber("SetValue", gyroDegree);
        
        SmartDashboard.putNumber("Power", 0.05*error);
        if (Drivetrain.gyroIO.getYaw() > gyroDegree) {
            drivetrain.drive(0, 0, 0.07*error);
        } else {
            drivetrain.drive(0, 0, -0.07*error);
        }
        if (Drivetrain.gyroIO.getYaw() > gyroDegree-1 && Drivetrain.gyroIO.getYaw() < gyroDegree+1) {
            done = true;
        }
        
    }

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
