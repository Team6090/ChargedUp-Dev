package frc.robot.commands.subcommandsaux.extension;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class ExtendArmO extends CommandBase {
  TelescopeSystem telescopeSystem;
  double power;

  public ExtendArmO(TelescopeSystem telescopeSystem, double power) {
    this.telescopeSystem = telescopeSystem;
    this.power = power;

    addRequirements(telescopeSystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    telescopeSystem.ExtendArmPO(power);
  }

  @Override
  public void end(boolean interrupted) {
    this.telescopeSystem.ExtendArmPO(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
