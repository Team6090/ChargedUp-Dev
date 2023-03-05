package frc.robot.subsystems.auxiliary;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.SubsystemConstants;
import frc.robot.subsystems.drivetrain.DrivetrainConstants;

public class IntakeSystem extends SubsystemBase {

  TalonFX armRetractMotor;
  VictorSP intakeMotor;
  Solenoid intakeSolenoidOn;
  Solenoid intakeSolenoidOff;
  double set;

  final double CONVERT_VALUE = 228.20996542875;

  static final I2C.Port i2cPort = I2C.Port.kOnboard;

  // static ColorSensorV3 IntakeSensor;

  // PixySystem pixySystem;

  boolean isIntakeSolenoidEnabled;

  CANCoder armRetractCANCoder;

  public IntakeSystem() {

    armRetractMotor = new TalonFX(DrivetrainConstants.ArmRetractionMotorID, "Aux");
    armRetractMotor.setNeutralMode(NeutralMode.Coast);
    
    armRetractCANCoder = new CANCoder(DrivetrainConstants.ArmRetractionCANCoderID, "Aux");

    armRetractMotor.configRemoteFeedbackFilter(armRetractCANCoder, 0);
    armRetractMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 2000);
    armRetractMotor.selectProfileSlot(0, 0);
    armRetractMotor.config_kP(0, 0.2, 2000);
    armRetractMotor.config_kI(0, 0.00002, 2000);
    armRetractMotor.config_kD(0, 0.0, 2000);
    armRetractMotor.config_kF(0, 0.2, 2000);
    armRetractMotor.configMotionCruiseVelocity(1500, 10);
    armRetractMotor.configMotionAcceleration(1000, 10);

    intakeMotor = new VictorSP(0);

    intakeSolenoidOn = new Solenoid(60, PneumaticsModuleType.REVPH, 0);
    intakeSolenoidOff = new Solenoid(60, PneumaticsModuleType.REVPH, 1);

    // IntakeSensor = new ColorSensorV3(i2cPort);

    // pixySystem = new PixySystem();

    set = armRetractMotor.getSelectedSensorPosition();
  }

  public void ExtendArmPO(double power) {
    armRetractMotor.set(TalonFXControlMode.PercentOutput, power);
  }

  public void ExtendArmToPosition(double cm) {
    double ec = convertToEncoderCounts(cm);
    armRetractMotor.set(TalonFXControlMode.MotionMagic, ec);
    set = cm;
  }

  public double GetArmExtendedPosition() {
    return convertToCM(armRetractMotor.getSelectedSensorPosition());
  }

  public void EnableIntakeSolenoid(boolean enable) {
    intakeSolenoidOn.set(enable);
    intakeSolenoidOff.set(!enable);
    isIntakeSolenoidEnabled = enable;
  }

  public void EnableIntakeMotor(boolean enable, boolean isReverse) {
    if (enable) {
      if (!isReverse) {
        intakeMotor.set(0.2);
      } else {
        intakeMotor.set(-0.2);
      }
    } else {
      intakeMotor.set(0);
    }
  }

  public void IntakeOn(double power, boolean reversed) {
    if (reversed != true) {
      intakeMotor.set(power);
    } else {
      intakeMotor.set(-power);
    }
  }

  public void IntakeOff() {
    intakeMotor.set(0);
  }

  // public boolean ObjectInIntake() {
  //   double prox = IntakeSensor.getProximity();
  //   if (prox > 100) {
  //     return true;
  //   }
  //   return false;
  // }

  // public String ObjectType() {
  //   if (ObjectInIntake()) {
  //     Color intakeSensorColor = IntakeSensor.getColor();
  //     if (intakeSensorColor.blue > intakeSensorColor.green
  //         || intakeSensorColor.blue > intakeSensorColor.red) {
  //       return "Cube";
  //     }
  //     return "Cone";
  //   } else {
  //     return "No Object";
  //   }
  // }

  private double convertToCM(double ec) {
    return (ec / CONVERT_VALUE) + 29;
  }

  private double convertToEncoderCounts(double cm) {
    return (cm - 29) * CONVERT_VALUE;
  }

  @Override
  public void periodic() {
    if (SubsystemConstants.Auxiliary.INTAKE_DEBUG == true) {
      // SmartDashboard.putBoolean("ObjectInIntake CV3", ObjectInIntake());
      // SmartDashboard.putString("Object Type CV3", ObjectType());
      // SmartDashboard.putNumber("Proximity CV3", IntakeSensor.getProximity());
      
    }else {
      SmartDashboard.putNumber("ArmExtensionPositionCM", convertToCM(armRetractCANCoder.getPosition()));
        SmartDashboard.putNumber("ArmExtendIntDC", armRetractMotor.getSelectedSensorPosition());
        SmartDashboard.putNumber("ArmExtendIntCM", convertToCM(armRetractMotor.getSelectedSensorPosition()));
    }
  }
}
