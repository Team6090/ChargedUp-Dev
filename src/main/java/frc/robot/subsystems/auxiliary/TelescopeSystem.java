package frc.robot.subsystems.auxiliary;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TelescopeSystem extends SubsystemBase {

  public TalonFX armRetractMotor;
  public CANCoder armRetractCANCoder;

  double set;

  public TelescopeSystem() {
    armRetractMotor = new TalonFX(50, "Aux");
    armRetractMotor.setNeutralMode(NeutralMode.Brake);
    armRetractCANCoder = new CANCoder(52, "Aux");

    armRetractMotor.configRemoteFeedbackFilter(armRetractCANCoder, 0);
    armRetractMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 2000);
    armRetractMotor.selectProfileSlot(0, 0);
    armRetractMotor.config_kP(0, 0.8, 2000);
    armRetractMotor.config_kI(0, 0.00002, 2000);
    armRetractMotor.config_kD(0, 0.0, 2000);
    armRetractMotor.config_kF(0, 0.2, 2000);
    armRetractMotor.configMotionCruiseVelocity(10500, 10); // 1500, 10500
    armRetractMotor.configMotionAcceleration(10000, 10); // 1000, 7000

    set = armRetractMotor.getSelectedSensorPosition();
  }

  public void ExtendArmPO(double power) {
    armRetractMotor.set(TalonFXControlMode.PercentOutput, power);
  }

  public void ExtendArmToPosition(double ec) {
    armRetractMotor.set(TalonFXControlMode.MotionMagic, ec);
    set = ec;
  }

  public double GetArmExtendedPosition() {
    return armRetractMotor.getSelectedSensorPosition();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("ArmExtendIntDC", armRetractMotor.getSelectedSensorPosition());
    SmartDashboard.putNumber("Velocity", armRetractMotor.getSelectedSensorVelocity());
    SmartDashboard.putNumber("Position", armRetractMotor.getSelectedSensorPosition());
  }
}
