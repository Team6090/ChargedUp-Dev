package frc.robot.commands.subautotele.pickup;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.commands.subcommandsaux.util.PickupStationFeed;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class PickupStation extends SequentialCommandGroup {

  public PickupStation(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    addCommands(
        new PickupStationFeed(pivotSystem, 3),
        new ArmExtension(intakeSystem, 50, true),
        new PivotMove(pivotSystem, 109, true)); // 108
    // new ArmExtension(intakeSystem, 5825, true)); //8825
  }
}
