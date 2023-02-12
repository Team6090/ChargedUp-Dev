package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotSystem;

public class PivotMove extends CommandBase {
    PivotSystem pivotSystem;
    double power;
    boolean done;

    public PivotMove (PivotSystem pivotSystem, double power){
        addRequirements(pivotSystem);
        this.pivotSystem = pivotSystem;
    }

    @Override
    public void initialize(){
        
    }

    @Override
    public void execute(){
        pivotSystem.PivotArm(power);
    }

    @Override
    public void end(boolean interrupted) {
        this.pivotSystem.PivotArm(0);
        super.end(interrupted);
    }

    @Override
  public boolean isFinished() {
    return done;
  }

}
