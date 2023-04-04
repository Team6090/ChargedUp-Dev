package frc.robot.commands.subcommandsaux.extension;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.auxiliary.TelescopeSystem;

public class ArmCheck extends CommandBase {

    TelescopeSystem telescopeSystem;

    boolean done = false;

    public ArmCheck(TelescopeSystem telescopeSystem) {
        this.telescopeSystem = telescopeSystem;

        addRequirements(telescopeSystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        SmartDashboard.putBoolean("Done", done);
        if (Math.abs(telescopeSystem.armRetractMotor.getSelectedSensorVelocity()) > 100) {
            done = true;
          }
    }

    @Override
    public void end(boolean interrupted) {
        telescopeSystem.ExtendArmPO(0);
        done = false;
    }

    @Override
    public boolean isFinished() {
        SmartDashboard.putBoolean("Done", done);
        return done;
    }

}
