package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drivetrain.DrivetrainConstants;

public class PivotSystem extends SubsystemBase {
    
    CANCoder pivotAngleCanCoder;

    Solenoid pivotSolenoidLock;

    TalonFX pivotMotorRight;
    TalonFX pivotMotorLeft;

    /* Instantiate the Motors and CANCoder */
    public PivotSystem() {
    pivotMotorLeft = new TalonFX(DrivetrainConstants.PivotMotorLeftID);
    pivotMotorRight = new TalonFX(DrivetrainConstants.PivotMotorRightID);
    pivotAngleCanCoder = new CANCoder(DrivetrainConstants.PivotAngleCanCoderID);
    pivotSolenoidLock = new Solenoid(DrivetrainConstants.PCMModule, PneumaticsModuleType.CTREPCM, DrivetrainConstants.PivotLockSolenoid);
    }

    /* Create code that moves the arm based on the power being applied */
    public void PivotArm(double power){
        pivotMotorLeft.set(TalonFXControlMode.Velocity, power);
        pivotMotorRight.set(TalonFXControlMode.Velocity, -power);
    }

    public void EnablePivotLock(boolean enable) {
        pivotSolenoidLock.set(enable);
    }

    /* Retrive the cancoder's position to find the pivot of the arm */
    public double GetArmPivot(){
        return pivotAngleCanCoder.getPosition();
    }

    @Override
    public void periodic() {
        /* If you have anything you want logged do it here */
    }

}
