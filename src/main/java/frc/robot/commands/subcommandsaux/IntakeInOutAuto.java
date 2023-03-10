package frc.robot.commands.subcommandsaux;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class IntakeInOutAuto extends CommandBase {

    IntakeSystem intakeSystem;

    boolean done = false;

    public IntakeInOutAuto(IntakeSystem intakeSystem) {
        this.intakeSystem = intakeSystem;

        addRequirements(intakeSystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if(intakeSystem.ObjectInIntake()) {
            intakeSystem.IntakeOn(0.4, true);
        } else {
            done = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        intakeSystem.IntakeOff();
        done = true;
    }

    @Override
    public boolean isFinished() {
        return done;
    } 

}
