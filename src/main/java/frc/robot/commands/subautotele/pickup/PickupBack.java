package frc.robot.commands.subautotele.pickup;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.ArmExtension;
import frc.robot.commands.subcommandsaux.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class PickupBack extends SequentialCommandGroup {

  public PickupBack(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    addCommands(
        new ArmExtension(intakeSystem, 50, true),
        new PivotMove(pivotSystem, 312.00, true),
        new ArmExtension(intakeSystem, 12250, true));
    // new IntakeInAuto(intakeSystem),
    // new HomePos(intakeSystem, pivotSystem));
  }
}
