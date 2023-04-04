package frc.robot.commands.subautotele.pickup;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.pivot.PivotArmToNextUp;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class PickupBackAuton extends SequentialCommandGroup {

  public PickupBackAuton(
      IntakeSystem intakeSystem, TelescopeSystem telescopeSystem, PivotSystem pivotSystem) {
    addCommands(
        new ArmExtension(telescopeSystem, Constants.EXTEND_HOME_POS, true),
        new PivotArmToNextUp(pivotSystem, 322, 180, true),
        // new PivotMove(pivotSystem, 322, true), // 325.99
        new ArmExtension(telescopeSystem, 7326, true));
  }
}
