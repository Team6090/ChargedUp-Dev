package frc.robot.commands.subautotele.pickup;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.commands.subcommandsaux.util.PickupStationFeed;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSubsystem;

public class PickupBack extends SequentialCommandGroup {

  public PickupBack(IntakeSystem intakeSystem, PivotSubsystem pivotSystem) {
    addCommands(
        new PickupStationFeed(pivotSystem, 2),
        new ArmExtension(intakeSystem, 50, true),
        new PivotMove(pivotSystem, 324, true), // 325.99
        new ArmExtension(intakeSystem, 7326, true));
  }
}
