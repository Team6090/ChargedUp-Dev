package frc.robot.commands.subautotele.pickup;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.commands.subcommandsaux.util.PickupStationFeed;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class PickupFront extends SequentialCommandGroup {

  public PickupFront(
      IntakeSystem intakeSystem, TelescopeSystem telescopeSystem, PivotSystem pivotSystem) {
    addCommands(
        new PickupStationFeed(pivotSystem, 1),
        new ArmExtension(telescopeSystem, Constants.EXTEND_HOME_POS, true),
        new PivotMove(pivotSystem, 54.67, true),
        new ArmExtension(telescopeSystem, 8799, true));
  }
}
