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

        m_TelescopeMotor.configFactoryDefault();

        m_TelescoreEncoder = new CANCoder(
            constants.TELESCOPE_ENCODER_ID,
            canbus
        );

        m_TelescoreEncoder.configFactoryDefault();

        talonFXDefaultConfiguration.ENABLE_SOFT_LIMIT_FORWARD = true;
        talonFXDefaultConfiguration.ENABLE_SOFT_LIMIT_REVERSE = true;
        talonFXDefaultConfiguration.FORWARD_SOFT_LIMIT = 25500;
        talonFXDefaultConfiguration.REVERSE_SOFT_LIMIT = 0;

        talonFXDefaultConfiguration.NEUTRAL_DEADBAND = 0.01;

        talonFXDefaultConfiguration.SENSOR_INITIALIZATION_STRATEGY = SensorInitializationStrategy.BootToAbsolutePosition;

        talonFXDefaultConfiguration.TIMEOUT = 2000;

        talonFXDefaultConfiguration.SLOT0_KP = 0.8;
        talonFXDefaultConfiguration.SLOT0_KI = 0.00002;
        talonFXDefaultConfiguration.SLOT0_KD = 0.0;
        talonFXDefaultConfiguration.SLOT0_KF = 0.2;

        TalonFXConfiguration defaultConfig = new TalonFXConfiguration();
        defaultConfig.forwardSoftLimitEnable = talonFXDefaultConfiguration.ENABLE_SOFT_LIMIT_FORWARD;
        defaultConfig.reverseSoftLimitEnable = talonFXDefaultConfiguration.ENABLE_SOFT_LIMIT_REVERSE;
        defaultConfig.forwardSoftLimitThreshold = talonFXDefaultConfiguration.FORWARD_SOFT_LIMIT;
        defaultConfig.reverseSoftLimitThreshold = talonFXDefaultConfiguration.REVERSE_SOFT_LIMIT;

        defaultConfig.neutralDeadband = talonFXDefaultConfiguration.NEUTRAL_DEADBAND;

        defaultConfig.initializationStrategy = talonFXDefaultConfiguration.SENSOR_INITIALIZATION_STRATEGY;

        defaultConfig.slot0.kP = talonFXDefaultConfiguration.SLOT0_KP;
        defaultConfig.slot0.kI = talonFXDefaultConfiguration.SLOT0_KI;
        defaultConfig.slot0.kD = talonFXDefaultConfiguration.SLOT0_KD;
        defaultConfig.slot0.kF = talonFXDefaultConfiguration.SLOT0_KF;

        m_TelescopeMotor.configAllSettings(defaultConfig, talonFXDefaultConfiguration.TIMEOUT);
        m_TelescopeMotor.clearStickyFaults(talonFXDefaultConfiguration.TIMEOUT);

        m_TelescopeMotor.selectProfileSlot(0, 0);

    }

    @Override
    public void periodic() {

    }
}