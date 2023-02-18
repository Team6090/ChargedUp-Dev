package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotSystem;

public class PivotMove extends CommandBase {

    PivotSystem pivotSystem;  
    double power, frontLimit, backLimit, armPivot;
    boolean done;

    public PivotMove (PivotSystem pivotSystem, double power){
        this.pivotSystem = pivotSystem;

        addRequirements(pivotSystem);

        frontLimit = 134.56;
        backLimit = -133.33;
        armPivot = pivotSystem.GetArmPivot();
    }

    @Override
    public void initialize(){
        
    }

    @Override
    public void execute(){
        armPivot = pivotSystem.GetArmPivot();

        if (armPivot >= frontLimit && power > 0){
          pivotSystem.PivotArm(0);
        } else if (armPivot <= backLimit && power < 0){
          pivotSystem.PivotArm(0);
        }else{
          pivotSystem.PivotArm(power);
        }

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
