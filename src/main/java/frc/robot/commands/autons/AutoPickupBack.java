package frc.robot.commands.autons;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.subautotele.pickup.PickupBack;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class AutoPickupBack extends CommandBase {

  IntakeSystem intakeSystem;
  TelescopeSystem telescopeSystem;
  PivotSystem pivotSystem;

  double currentPos;

  boolean cone;

  boolean done = false;

  public AutoPickupBack(
      IntakeSystem intakeSystem,
      TelescopeSystem telescopeSystem,
      PivotSystem pivotSystem,
      boolean cone) {
    this.intakeSystem = intakeSystem;
    this.telescopeSystem = telescopeSystem;
    this.pivotSystem = pivotSystem;
    this.cone = cone;

    addRequirements(intakeSystem, telescopeSystem, pivotSystem);
  }

  @Override
  public void initialize() {
    if (cone == true) {
      intakeSystem.IntakeOn(0.75, true);
    } else {
      intakeSystem.EnableIntakeSolenoid(false);
      intakeSystem.IntakeOn(0.75, true);
    }
    new PickupBack(intakeSystem, telescopeSystem, pivotSystem).ignoringDisable(true).schedule();
    done = true;
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    done = false;
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
