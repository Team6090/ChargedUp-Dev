package frc.robot.commands.subcommandsbase;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class LockRobotDrivetrain extends SequentialCommandGroup {

  public LockRobotDrivetrain(Drivetrain drivetrain) {
    addCommands(new XStance(drivetrain, true));
  }
}
