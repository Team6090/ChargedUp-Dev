package frc.robot.commands.subcommandsaux.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class IntakeBackAutoPath extends CommandBase {
    
    IntakeSystem intakeSystem;

    boolean object;

    boolean done = false;

    public IntakeBackAutoPath(IntakeSystem intakeSystem) {
        this.intakeSystem = intakeSystem;

        addRequirements(intakeSystem);
    }

    @Override
    public void initialize() {
        object = intakeSystem.ObjectInIntake();
    }

    @Override
    public void execute() {
        object = intakeSystem.ObjectInIntake();
        if(object == false) {
            intakeSystem.IntakeOn(0.7, false);
        }else {
            intakeSystem.IntakeOff();
        }
    }

    @Override
    public void end(boolean interrupted) {
        done = false;
    }

    public boolean isFinished() {
        return done;
    }

}
