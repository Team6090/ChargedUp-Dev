package frc.robot.commands.subautotele.pickup;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.ArmExtension;
import frc.robot.commands.subcommandsaux.IntakeInAuto;
import frc.robot.commands.subcommandsaux.PivotMove;
import frc.robot.commands.teleop.stage.HomePos;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class PickupFront extends SequentialCommandGroup {

  public PickupFront(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
    addCommands(
        new ArmExtension(intakeSystem, 50, true),
        new PivotMove(pivotSystem, 51.67, true),
        new ArmExtension(intakeSystem, 8799, true));
        // new IntakeInAuto(intakeSystem),
        // new HomePos(intakeSystem, pivotSystem));
  }
}
