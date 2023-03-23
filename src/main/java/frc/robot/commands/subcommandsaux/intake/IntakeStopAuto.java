package frc.robot.commands.subcommandsaux.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class IntakeStopAuto extends CommandBase {

    IntakeSystem intakeSystem;

    Timer timer;

    boolean done = false;

    public IntakeStopAuto(IntakeSystem intakeSystem) {
        this.intakeSystem = intakeSystem;
        timer = new Timer();

        addRequirements(intakeSystem);
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public void execute() {
        if(intakeSystem.ObjectInIntake() == true || timer.get() > 0.5) {
            done = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        intakeSystem.IntakeOff();
        done = false;
    }

    @Override
    public boolean isFinished() {
        return done;
    }

}