package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.intake.IntakeOpenClose;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class ScoreConeOverride extends CommandBase {
    
    PivotSystem pivotSystem;
    IntakeSystem intakeSystem;

    double currentPos;

    boolean done = false;

    public ScoreConeOverride(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
        this.intakeSystem = intakeSystem;
        this.pivotSystem = pivotSystem;

        addRequirements(intakeSystem, pivotSystem);
    }

    @Override
    public void initialize() {
        currentPos = pivotSystem.GetArmDeg();
        Commands.sequence(
            new PivotMove(pivotSystem, currentPos-5, true),
            new WaitCommand(.1),
            new IntakeOpenClose(intakeSystem, false),
            new WaitCommand(.1),
            new ArmExtension(intakeSystem, Constants.EXTEND_HOME_POS, true),
            new IntakeOpenClose(intakeSystem, true),
            new PivotMove(pivotSystem, 30, true)    
        ).schedule();
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
