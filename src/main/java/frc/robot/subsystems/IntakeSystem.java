package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drivetrain.DrivetrainConstants;
import frc.robot.subsystems.pixy.PixySystem;

public class IntakeSystem extends SubsystemBase {

  VictorSP intakeMotor;
  Solenoid intakeSolenoid;

  static final I2C.Port i2cPort = I2C.Port.kOnboard;

  static ColorSensorV3 IntakeSensor = new ColorSensorV3(i2cPort);

  PixySystem pixySystem;

  boolean isIntakeSolenoidEnabled;

  public IntakeSystem() {
    intakeMotor = new VictorSP(DrivetrainConstants.IntakeMotor);
    intakeSolenoid =
        new Solenoid(
            DrivetrainConstants.PCMModule,
            PneumaticsModuleType.CTREPCM,
            DrivetrainConstants.IntakeSolenoid);
    pixySystem = new PixySystem();
  }

  public void EnableIntakeSolenoid(boolean enable) {
    intakeSolenoid.set(enable);
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

  public boolean DetectedObject() {
    if (pixySystem.ObjectCount() > 0) {
      return true;
    }
    return false;
  }

  public int GetCurrentObjectSig() {
    return pixySystem.GetObjectSig();
  }

  public boolean ObjectInIntake() {
    double prox = IntakeSensor.getProximity();
    if (prox > 100) {
      return true;
    }
    return false;
  }

  public String ObjectType(){
    if (ObjectInIntake()) {
        Color intakeSensorColor = IntakeSensor.getColor();
        if (intakeSensorColor.blue > intakeSensorColor.green || intakeSensorColor.blue > intakeSensorColor.red) {return "Cube"; }
        return "Cone";
    }else{
        return "No Object";
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Prox", IntakeSensor.getProximity());
    SmartDashboard.putNumber("Red", IntakeSensor.getRed());
    SmartDashboard.putNumber("Green", IntakeSensor.getGreen());
    SmartDashboard.putNumber("Blue", IntakeSensor.getBlue());
    SmartDashboard.putBoolean("ObjectInIntake", ObjectInIntake());
    SmartDashboard.putString("Object Type", ObjectType());

    SmartDashboard.putBoolean("Object Seen", DetectedObject());
  }
}
