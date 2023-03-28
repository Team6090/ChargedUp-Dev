package frc.robot.commands.teleop.stage.cone;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.intake.IntakeOpenClose;
import frc.robot.commands.subcommandsaux.pivot.PivotArmToNextUp;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class ConeStage3Auto extends SequentialCommandGroup {

  public ConeStage3Auto(
      IntakeSystem intakeSystem,
      TelescopeSystem telescopeSystem,
      PivotSystem pivotSystem) { // HomePos to HighPole

    addCommands(
        new ArmExtension(telescopeSystem, 0, true),
        new PivotArmToNextUp(pivotSystem, 115.73, 60.14, true),
        new ArmExtension(telescopeSystem, 24515, true),
        new PivotMove(pivotSystem, 107.66, true),
        new WaitCommand(0.15),
        new IntakeOpenClose(intakeSystem, false));
  }
}
