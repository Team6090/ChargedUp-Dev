package frc.robot.commands.subcommandsaux.development;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class MoveArmToLocationByPath extends CommandBase {

  private TelescopeSystem telescopeSystem;
  private PivotSystem pivotSystem;
  private double[][] path;

  public MoveArmToLocationByPath(
      TelescopeSystem telescopeSystem, PivotSystem pivotSystem, double[][] path) {
    this.telescopeSystem = telescopeSystem;
    this.pivotSystem = pivotSystem;
    this.path = path;

    addRequirements(telescopeSystem, pivotSystem);
  }

  @Override
  public void initialize() {
    telescopeSystem.ExtendArmPO(0);
    pivotSystem.PivotArm(0, false);
    for (int i = 0; i < path.length; i++) {
      SmartDashboard.putNumber("Iteration", i);
      telescopeSystem.ExtendArmToPosition(path[i][0]);
      pivotSystem.PivotArmToPosition(path[i][1]);
      new WaitCommand(.1);
    }
    super.end(true);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
