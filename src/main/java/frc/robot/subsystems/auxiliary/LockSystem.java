package frc.robot.subsystems.auxiliary;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LockSystem extends SubsystemBase {

  Solenoid openLock;
  Solenoid closeLock;

  public boolean locked;

  public LockSystem() {
    openLock = new Solenoid(60, PneumaticsModuleType.REVPH, 3);
    closeLock = new Solenoid(60, PneumaticsModuleType.REVPH, 2);
  }

  public void extendLock(boolean enabled) {
    locked = enabled;
    openLock.set(!enabled);
    closeLock.set(enabled);
  }
}
