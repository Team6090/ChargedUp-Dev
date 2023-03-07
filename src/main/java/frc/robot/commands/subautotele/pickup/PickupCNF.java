package frc.robot.commands.subautotele.pickup;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.ArmExtension;
import frc.robot.commands.subcommandsaux.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class PickupCNF extends SequentialCommandGroup {
    
    public PickupCNF(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
        addCommands(
            // new ArmExtension(intakeSystem, 400, true),
            // new PivotMove(pivotSystem, 51.67, true),
            // new ArmExtension(intakeSystem, 8799, true)
            new ArmExtension(intakeSystem, 400, true),
            new PivotMove(pivotSystem, 120, true),
            new ArmExtension(intakeSystem, 22000, true)
        );
    }

}