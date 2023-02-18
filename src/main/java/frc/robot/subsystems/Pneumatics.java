package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drivetrain.DrivetrainConstants;

public class Pneumatics extends SubsystemBase {
  Compressor compressor;
  PneumaticHub pneumaticHub;
  AnalogPotentiometer analogPressureSensor;

  public Pneumatics() {
    //compressor = new Compressor(DrivetrainConstants.PCMModule, PneumaticsModuleType.CTREPCM);
    pneumaticHub = new PneumaticHub(1);
    analogPressureSensor = new AnalogPotentiometer(0,120,0);

    /* Enable or Disable on Robot Startup */
    compressor.enableDigital();
  }

  public double GetPressure() {
    return compressor.getPressure();
  }

  public void EnableCompressor(boolean enable) {
    if (enable) {
      compressor.enableDigital();
      return;
    }
    compressor.disable();
  }

  @Override
  public void periodic() {}
}
