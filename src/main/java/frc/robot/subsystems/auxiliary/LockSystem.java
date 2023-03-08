package frc.robot.subsystems.auxiliary;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LockSystem extends SubsystemBase {

  Solenoid openLock;
  Solenoid closeLock;

  public LockSystem() {
    openLock = new Solenoid(PneumaticsModuleType.REVPH, 3);
    closeLock = new Solenoid(PneumaticsModuleType.REVPH, 2);
  }

  public void extendLock(boolean enabled) {
    openLock.set(!enabled);
    // closeLock.set(enabled);
  }
}
