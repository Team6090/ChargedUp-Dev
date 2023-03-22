package frc.robot.commands.subautotele.pickup;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.commands.subcommandsaux.util.PickupStationFeed;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class PickupFront extends SequentialCommandGroup {

  public PickupFront(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    addCommands(
        new PickupStationFeed(pivotSystem, 1),
        new ArmExtension(intakeSystem, 50, true),
        new PivotMove(pivotSystem, 52.67, true),
        new ArmExtension(intakeSystem, 8799, true));
  }
}
