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
import frc.robot.subsystems.SystemConstants;
import frc.robot.subsystems.drivetrain.DrivetrainConstants;
import java.io.PrintStream;

public class IntakeSystem extends SubsystemBase {

  TalonFX armRetractMotor;
  VictorSP intakeMotor;
  Solenoid intakeSolenoidOn;
  Solenoid intakeSolenoidOff;
  double set;

  int currentObject;

  PrintStream printStream;

  // public int currentObject = 0; // 0: No Object, 1: Cone, 2: Cube

  final double CONVERT_VALUE = 228.20996542875;

  final double MIN_POSITION = 210;
  final double MAX_POSITION = 10000;

  static final I2C.Port i2cPort = I2C.Port.kMXP;

  static ColorSensorV3 IntakeSensor;

  PixySystem pixySystem;

  boolean isIntakeSolenoidEnabled;

  CANCoder armRetractCANCoder;

  public IntakeSystem() {
    armRetractMotor = new TalonFX(DrivetrainConstants.ArmRetractionMotorID, "Aux");
    armRetractMotor.setNeutralMode(NeutralMode.Brake);

    armRetractCANCoder = new CANCoder(DrivetrainConstants.ArmRetractionCANCoderID, "Aux");

    armRetractMotor.configRemoteFeedbackFilter(armRetractCANCoder, 0);
    armRetractMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 2000);
    armRetractMotor.selectProfileSlot(0, 0);
    armRetractMotor.config_kP(0, 0.8, 2000);
    armRetractMotor.config_kI(0, 0.00002, 2000);
    armRetractMotor.config_kD(0, 0.0, 2000);
    armRetractMotor.config_kF(0, 0.2, 2000);
    armRetractMotor.configMotionCruiseVelocity(10500, 10); // 1500, 10500
    armRetractMotor.configMotionAcceleration(10000, 10); // 1000, 7000

    intakeMotor = new VictorSP(0);

    intakeSolenoidOn = new Solenoid(60, PneumaticsModuleType.REVPH, 0);
    intakeSolenoidOff = new Solenoid(60, PneumaticsModuleType.REVPH, 1);

    IntakeSensor = new ColorSensorV3(i2cPort);

    pixySystem = new PixySystem();

    set = armRetractMotor.getSelectedSensorPosition();
    // armRetractCANCoder.setPosition(0);
    // armRetractCANCoder.setPosition(armRetractCANCoder.getAbsolutePosition());
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

  public boolean ObjectInIntake() {
    double prox = IntakeSensor.getProximity();
    if (prox > 100) {
      return true;
    }
    return false;
  }

  public int ObjectType() {
    if (ObjectInIntake()) {
      Color intakeSensorColor = IntakeSensor.getColor();
      if (intakeSensorColor.blue > intakeSensorColor.green
          || intakeSensorColor.blue > intakeSensorColor.red) {
        return 2;
      }
      return 1;
    } else {
      return 0;
    }
  }

  // public int ObjectType() {
  //   if (pixySystem.GetConeCount() == 0 && pixySystem.GetCubeCount() == 0) {
  //     return 0;
  //   } else if (pixySystem.GetConeCount() > pixySystem.GetCubeCount()) {
  //     return 1;
  //   } else {
  //     return 2;
  //   }
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

  // private double convertToCM(double ec) {
  //   return 87.2 + .00334*ec -.00000000762*(ec*ec);
  // }

  // private double convertToEncoderCounts(double cm) {
  //   return (cm - 29) * CONVERT_VALUE;
  // }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Set", set);
    // if (SubsystemConstants.SystemConstants.INTAKE_DEBUG == true) {

    // } else {
      // SmartDashboard.putBoolean("ObjectInIntake CV3", ObjectInIntake());
      // SmartDashboard.putNumber("Object Type CV3", ObjectType());
      // SmartDashboard.putNumber("Proximity CV3", IntakeSensor.getProximity());
      // SmartDashboard.putNumber("ArmExtensionPositionCM",
      // convertToCM(armRetractCANCoder.getPosition()));
      SmartDashboard.putNumber("ArmExtendIntDC", armRetractMotor.getSelectedSensorPosition());
      // SmartDashboard.putNumber("ArmExtendIntCM",
      // convertToCM(armRetractMotor.getSelectedSensorPosition()));

    // }
    // currentObject = ObjectType();
    // SmartDashboard.putNumber("ObjectType", currentObject); // Change to command call
    // if (printStream == null) {
    //   try {
    //     printStream = new PrintStream(new File("/home/lvuser", "pid.csv"));
    //   } catch (FileNotFoundException e) {
    //     // TODO Auto-generated catch block
    //     e.printStackTrace();
    //   }
    // }
    // printStream.println(set + "," + armRetractMotor.getSelectedSensorPosition());
  }
}
