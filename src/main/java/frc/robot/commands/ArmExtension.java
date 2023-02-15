package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSystem;

public class ArmExtension extends CommandBase{
    ArmSystem armSystem;
    boolean done;
    double power;

    public ArmExtension (ArmSystem armSystem, double power){
        addRequirements(armSystem);
        this.armSystem = armSystem;
    }

    @Override
    public void initialize(){
        
    }

    @Override
    public void execute(){
        armSystem.MoveArm(power);
    }

    @Override
    public void end(boolean interrupted) {
        this.armSystem.MoveArm(0);;
        super.end(interrupted);
    }

    @Override
  public boolean isFinished() {
    return done;
  }

}
