package frc.robot.commands.subcommandsaux;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.IntakeSystem;

public class OpenThenClose extends CommandBase {

  IntakeSystem intakeSystem;

  Timer timer;

  boolean done = false;

  public OpenThenClose(IntakeSystem intakeSystem) {
    this.intakeSystem = intakeSystem;

    addRequirements(intakeSystem);
  }

  @Override
  public void initialize() {
    timer.start();
    intakeSystem.EnableIntakeSolenoid(false);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    // LOSER
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
