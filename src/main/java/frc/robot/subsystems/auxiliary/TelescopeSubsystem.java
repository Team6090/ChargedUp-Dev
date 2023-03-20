package frc.robot.subsystems.auxiliary;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.SuperSubsystem.TalonFXConfigurationDefault;
import frc.robot.subsystems.SystemConstants.AuxiliaryConstants.TelescopeConstants;

/** TelescopeSubsystem: Controls extension and retraction of the arm through the use of a TalonFX (Falcon500) motor and a CANCoder
 * this subsystem can control the positioning of the arm's extension/retraction. The CANCoder is used as a remote sensor for the
 * TalonFX to get and set current position of the arm.
 * 
 * @category Subsystem
 * 
 * @see SubsystemBase
 * 
 * @version 1.0.0
 * 
 * @since 1.0.0
 * 
 * @author AFR
 */
@SuppressWarnings("unused")
public class TelescopeSubsystem extends SubsystemBase {

    private TelescopeConstants constants;

    private TalonFX m_TelescopeMotor;

    private CANCoder m_TelescoreEncoder;
   
    private TalonFXConfigurationDefault talonFXDefaultConfiguration = new TalonFXConfigurationDefault();
    private TalonFXConfigurationDefault debugConfiguration = new TalonFXConfigurationDefault();

    public TelescopeSubsystem(String canbus) {
        m_TelescopeMotor = new TalonFX(
            constants.TELESCOPE_MOTOR_ID,
            canbus
        );

        m_TelescoreEncoder = new CANCoder(
            constants.TELESCOPE_ENCODER_ID,
            canbus
        );
    }

    @Override
    public void periodic() {

    }
}