package frc.robot.commands.subcommandsaux;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.vision.AlignToAprilTagX;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class TurnAlign extends SequentialCommandGroup {

  public TurnAlign(Drivetrain drivetrain, double gyro) {
    addCommands(new TurnToGyro(drivetrain, -180), new AlignToAprilTagX(drivetrain));
  }
}
