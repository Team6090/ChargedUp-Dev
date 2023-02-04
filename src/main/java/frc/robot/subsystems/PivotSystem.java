package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PivotSystem extends SubsystemBase {
    
    CANCoder pivotAngleCanCoder;

    TalonFX pivotMotorRight;
    TalonFX pivotMotorLeft;

    /* Instantiate the Motors and CANCoder */
    public PivotSystem() {
        
    }

    /* Create code that moves the arm based on the power being applied */
    public void PivotArm(double power){

    }

    /* Retrive the cancoder's position to find the pivot of the arm */
    public double GetArmPivot(){
        return 0.0;
    }

    @Override
    public void periodic() {
        /* If you have anything you want logged do it here */
    }

}
