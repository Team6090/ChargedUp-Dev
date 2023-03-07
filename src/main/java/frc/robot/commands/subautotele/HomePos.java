package frc.robot.commands.subautotele;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.ArmExtension;
import frc.robot.commands.subcommandsaux.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;

public class HomePos extends SequentialCommandGroup {
    
    public HomePos(IntakeSystem intakeSystem, PivotSystem pivotSystem) {
        addCommands(
            new ArmExtension(intakeSystem, 400, true),
            new PivotMove(pivotSystem, 30, true)
        );
    }

}