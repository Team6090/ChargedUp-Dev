package frc.robot.commands.subcommandsaux;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class MoveArmToLocationByPath extends CommandBase {

  private IntakeSystem intakeSystem;
  private PivotSystem pivotSystem;
  private double[][] path;

  public MoveArmToLocationByPath(
      IntakeSystem intakeSystem, PivotSystem pivotSystem, double[][] path) {
    this.intakeSystem = intakeSystem;
    this.pivotSystem = pivotSystem;
    this.path = path;

    addRequirements(intakeSystem, pivotSystem);
  }

  @Override
  public void initialize() {
    intakeSystem.ExtendArmPO(0);
    pivotSystem.PivotArm(0, false);
    for (int i = 0; i < path.length; i++) {
      SmartDashboard.putNumber("Iteration", i);
      intakeSystem.ExtendArmToPosition(path[i][0]);
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
