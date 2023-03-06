package frc.robot.subsystems.auxiliary;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AirCompressor extends SubsystemBase {
  Compressor m_compressor;
  // public static Solenoid lockOpen;
  // public static Solenoid lockClose;
  /** Creates a new Pneumatics. */
  public AirCompressor() {
    m_compressor = new Compressor(60, PneumaticsModuleType.REVPH);
    // lockClose = new Solenoid(PneumaticsModuleType.REVPH, 2); // TODO: Make as External Subsystem as higher system
    // lockOpen = new Solenoid(PneumaticsModuleType.REVPH, 3);
    m_compressor.enableDigital();
  }

  /**
   * @param compressor
   * @return Pressure
   */
  public double getPressure(Compressor compressor) {
    this.m_compressor = compressor;
    return compressor.getPressure();
  }

  public void startCompressor() {
    m_compressor.enableDigital();
  }

  public void stopCompressor() {
    m_compressor.disable();
  }

  // public static void extendLock(boolean enabled) {
  //   lockOpen.set(!enabled);
  //   lockClose.set(enabled);
  // }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
