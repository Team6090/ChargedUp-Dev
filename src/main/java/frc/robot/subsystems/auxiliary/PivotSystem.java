package frc.robot.subsystems.auxiliary;

import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.SubsystemConstants;
import frc.robot.subsystems.drivetrain.DrivetrainConstants;

public class PivotSystem extends SubsystemBase {

  TalonFX pivotMotorLeft;
  TalonFX pivotMotorRight;

  CANCoder pivotAngleCanCoder;

  DigitalInput limitSwitch;

  ArmFeedforward feedforward;

  public int currentStage = 0;

  final double BOUND_LIMIT = 3000;

  public PivotSystem() {
    limitSwitch = new DigitalInput(0);

    feedforward =
        new ArmFeedforward(
            0.24747, 0.65388, 0.28999,
            0.098095); // 0.14793, 0.15081, 0.032265, 0.0010962 2/25/2023 1:00PM

    pivotMotorLeft = new TalonFX(DrivetrainConstants.PivotMotorLeftID, "Aux");
    pivotMotorRight = new TalonFX(DrivetrainConstants.PivotMotorRightID, "Aux");
    pivotAngleCanCoder = new CANCoder(DrivetrainConstants.PivotAngleCanCoderID, "Aux");

    pivotMotorRight.configRemoteFeedbackFilter(pivotAngleCanCoder, 0);
    pivotMotorLeft.configRemoteFeedbackFilter(pivotAngleCanCoder, 0);

    pivotMotorRight.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 2000);
    pivotMotorRight.selectProfileSlot(0, 0);
    pivotMotorRight.config_kP(0, 1.0, 2000); // 3.0
    pivotMotorRight.config_kI(0, 0.00005, 2000);
    pivotMotorRight.config_kD(0, 0.001, 2000);
    pivotMotorRight.configMotionCruiseVelocity(600, 10); // 600
    pivotMotorRight.configMotionAcceleration(450, 10); // 450
    pivotMotorRight.configMotionSCurveStrength(1);

    pivotMotorLeft.setNeutralMode(NeutralMode.Brake);
    pivotMotorRight.setNeutralMode(NeutralMode.Brake);
    pivotMotorLeft.follow(pivotMotorRight);
    // pivotAngleCanCoder.setPosition(pivotAngleCanCoder.getAbsolutePosition());
  }

  public void PivotArm(double power, boolean reversed) {
    pivotMotorRight.setInverted(reversed);
    pivotMotorLeft.setInverted(reversed);
    pivotMotorRight.set(TalonFXControlMode.PercentOutput, power);
  }

  public void HoldArm(boolean hold) {
    if (hold == true) {
      pivotMotorRight.set(
          TalonFXControlMode.MotionMagic, pivotMotorRight.getSelectedSensorPosition());
    } else {
      pivotMotorRight.set(TalonFXControlMode.PercentOutput, 0);
    }
  }

  public void PivotArmDeg(double deg) {
    double encoderCounts = convertToEncoderCounts(deg);
    pivotMotorRight.set(TalonFXControlMode.MotionMagic, encoderCounts);
    SmartDashboard.putNumber("EncoderCount", encoderCounts);
    SmartDashboard.putNumber("SetDeg", deg);
  }

  public double GetArmDeg() {
    return convertToDegrees(pivotMotorRight.getSelectedSensorPosition());
  }

  public boolean GetLimitSwitchOutput() {
    return limitSwitch.get();
  }

  public void PivotArmToPosition(double pos) {
    pivotMotorRight.set(
        TalonFXControlMode.MotionMagic,
        pos,
        DemandType.ArbitraryFeedForward,
        calculateFeedforward());
    pivotMotorLeft.follow(pivotMotorRight);
  }

  public double GetArmPivotCanCoder() {
    return pivotAngleCanCoder.getAbsolutePosition();
  }

  public double GetArmPivotIntegratedSensor() {
    return pivotMotorRight.getSelectedSensorPosition(0);
  }

  public boolean CheckIntegratedSensorBounds(double positionToCheck) {
    double lb = positionToCheck - BOUND_LIMIT;
    double ub = positionToCheck + BOUND_LIMIT;

    if (pivotMotorRight.getSelectedSensorPosition(0) < ub
        && pivotMotorRight.getSelectedSensorPosition(0) > lb) {
      return true;
    }
    return false;
  }

  private double calculateFeedforward() {
    double feed =
        feedforward.calculate(
            Math.toRadians(pivotAngleCanCoder.getAbsolutePosition()) - 90,
            pivotMotorRight.getSelectedSensorVelocity());

    if (feed > 1) {
      return 1;
    }
    return feed;
  }

  private double convertToDegrees(double in) {
    return in * (360.0 / 4096.0);
  }

  private double convertToEncoderCounts(double in) {
    return in * (4096.0 / 360.0);
  }

  public void SetCurrentStage(int stage) {
    currentStage = stage;
  }

  public int GetCurrentStage() {
    return currentStage;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("CurrentStage", currentStage);
    if (SubsystemConstants.Auxiliary.PIVOT_DEBUG == true) {
      SmartDashboard.putNumber(
          "PivotPositionDegrees", convertToDegrees(pivotMotorRight.getSelectedSensorPosition()));
      SmartDashboard.putNumber("PivotPositionEC", pivotMotorRight.getSelectedSensorPosition());
      SmartDashboard.putBoolean("LimitSwitch", GetLimitSwitchOutput());
    } else {

    }
  }
}
