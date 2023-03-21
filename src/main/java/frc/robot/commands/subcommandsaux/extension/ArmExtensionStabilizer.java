package frc.robot.commands.subcommandsaux.extension;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class ArmExtensionStabilizer extends CommandBase {
    
    IntakeSystem intakeSystem;

    Timer timer;

    double prevPos, newPos;
    double prevTime, newTime;

    double velo;

    boolean done = false;

    public ArmExtensionStabilizer(IntakeSystem intakeSystem) {
        this.intakeSystem = intakeSystem;

        timer = new Timer();

        addRequirements(intakeSystem);
    }

    @Override
    public void initialize() {
        timer.start();
        newTime = timer.get();
        newPos = intakeSystem.GetArmExtendedPosition();
        velo = intakeSystem.GetArmExtentedVelo();
    }

    @Override
    public void execute() {
        prevTime = newTime;
        prevPos = newPos;
        newTime = timer.get();
        newPos = intakeSystem.GetArmExtendedPosition();

        double change = (newPos-prevPos)/(newTime-prevPos);

        if (velo > 1 && change < 1) {
            
        }

    }

    @Override
    public void end(boolean interrupted) {
        done = false;
    }

    @Override
    public boolean isFinished() {
        return done;
    }

}
