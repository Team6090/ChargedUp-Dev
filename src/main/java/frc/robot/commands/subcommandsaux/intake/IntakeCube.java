package frc.robot.commands.subcommandsaux.intake;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class IntakeCube extends CommandBase {

    IntakeSystem intakeSystem;
    DoubleSupplier trigger;

    public IntakeCube(IntakeSystem intakeSystem, DoubleSupplier trigger) {
        this.intakeSystem = intakeSystem;
        this.trigger = trigger;

        addRequirements(intakeSystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(deadband(trigger.getAsDouble(), .2) > 0.0){
            run();
        } else {
            stop();
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    private void run(){
        intakeSystem.EnableIntakeSolenoid(false);
        intakeSystem.IntakeOn(0.75, false);
    }

    private void stop(){
        intakeSystem.IntakeOff();
        intakeSystem.EnableIntakeSolenoid(true);
    }

    private static double deadband(double value, double deadband) {
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