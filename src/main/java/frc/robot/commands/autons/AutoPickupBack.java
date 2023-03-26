package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants;
import frc.robot.commands.subautotele.pickup.PickupBack;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class AutoPickupBack extends CommandBase {
    
    IntakeSystem intakeSystem;
    PivotSystem pivotSystem;

    double currentPos;

    boolean cone;

    boolean done = false;

    public AutoPickupBack(IntakeSystem intakeSystem, PivotSystem pivotSystem, boolean cone) {
        this.intakeSystem = intakeSystem;
        this.pivotSystem = pivotSystem;
        this.cone = cone;

        addRequirements(intakeSystem, pivotSystem);
    }

    @Override
    public void initialize() {
        if (cone == true) {
            intakeSystem.IntakeOn(0.75, true);
        }else {
            intakeSystem.EnableIntakeSolenoid(false);
            intakeSystem.IntakeOn(0.75, true);
        }
        new PickupBack(intakeSystem, pivotSystem).ignoringDisable(true).schedule();
        done = true;
    }

    @Override
    public void execute() {
        
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
