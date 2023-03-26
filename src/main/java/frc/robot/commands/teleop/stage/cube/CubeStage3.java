package frc.robot.commands.teleop.stage.cube;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class CubeStage3 extends SequentialCommandGroup {

  public CubeStage3(
      IntakeSystem intakeSystem, TelescopeSystem telescopeSystem, PivotSystem pivotSystem) {
    addCommands(
        new ArmExtension(telescopeSystem, 0, true),
        new PivotMove(pivotSystem, 106.5, true),
        new ArmExtension(telescopeSystem, 21276, true));
  }
}
