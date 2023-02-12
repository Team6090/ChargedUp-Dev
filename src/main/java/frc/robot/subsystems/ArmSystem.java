package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drivetrain.DrivetrainConstants;

public class ArmSystem extends SubsystemBase {

  TalonFX armRetractMotor;

  CANCoder armRetractCANCoder;

  public ArmSystem() {
    armRetractMotor = new TalonFX(DrivetrainConstants.ArmRetractionMotorID);
    armRetractCANCoder = new CANCoder(DrivetrainConstants.ArmRetractionCANCoderID);
  }

  public void MoveArm(double setPoint) {
    /* Theory Not True, Change to correction with cancoder */
    armRetractMotor.set(TalonFXControlMode.PercentOutput, setPoint);
  }

  public double GetArmPosition() {
    /* May be abosolute or not */
    return armRetractCANCoder.getAbsolutePosition();
  }

  @Override
  public void periodic() {
    /* Log anything to check code */
  }
}
