package frc.robot.subsystems.auxiliary;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.SuperSystem.TalonFXConfigurationDefault;
import frc.robot.subsystems.SystemConstants.AuxiliaryConstants.TelescopeConstants;

/**
 * TelescopeSubsystem: Controls extension and retraction of the arm through the use of a TalonFX
 * (Falcon500) motor and a CANCoder this subsystem can control the positioning of the arm's
 * extension/retraction. The CANCoder is used as a remote sensor for the TalonFX to get and set
 * current position of the arm.
 *
 * @category Subsystem
 * @see SubsystemBase
 * @version 1.0.0
 * @since 1.0.0
 * @author AFR
 */
public class TelescopeSubsystem extends SubsystemBase {

  // Constants
  private TelescopeConstants constants;

  // Devices
  private TalonFX m_TelescopeMotor;
  private CANCoder m_TelescoreEncoder;

  // Feedback Values
  double newSetPosition, prevSetPosition;
  double currentPosition;

  private TalonFXConfigurationDefault talonFXDefaultConfiguration =
      new TalonFXConfigurationDefault();
  private TalonFXConfigurationDefault debugConfiguration = new TalonFXConfigurationDefault();

  /**
   * Telescope Subsystem Constructor
   *
   * @category Constructor
   * @param canbus The canbus name of all devices in the subsystem use
   * @version 1.0.0
   * @since 1.0.0
   * @author AFR
   */
  public TelescopeSubsystem(String canbus) {
    m_TelescopeMotor = new TalonFX(constants.TELESCOPE_MOTOR_ID, canbus);

    m_TelescoreEncoder = new CANCoder(constants.TELESCOPE_ENCODER_ID, canbus);

    m_TelescopeMotor.configRemoteFeedbackFilter(m_TelescoreEncoder, 0);
    m_TelescopeMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 10);
  }

  /**
   * Terminates Subsystem: Use only in emergency occasions to stop all motor and encoder control and
   * feedbank. The motor and encoder controllers will be deconstructed following with the subsystem
   * being unregistered from the {@link CommandScheduler}
   *
   * @version 1.0.0
   * @since 1.0.0
   * @author AFR
   */
  public void terminate() {
    m_TelescopeMotor.set(TalonFXControlMode.Disabled, 0);
    m_TelescopeMotor.DestroyObject();
    m_TelescoreEncoder.DestroyObject();
    CommandScheduler.getInstance().unregisterSubsystem(this);
  }

  /**
   * Sets the position of the m_TelescopeMotor
   *
   * @param positionInEncoderCounts Desired set point for the m_TelescopeMotor
   * @version 1.0.0
   * @since 1.0.0
   * @author AFR
   */
  public void setPosition(int positionInEncoderCounts) {
    _updateSetPositions(positionInEncoderCounts);
    m_TelescopeMotor.set(TalonFXControlMode.MotionMagic, positionInEncoderCounts);
  }

  /**
   * Sets the position of the m_TelescopeMotor; Returns the prevSetPoint
   *
   * @param positionInEncoderCounts Desired set point for the m_TelescopeMotor
   * @version 1.0.0
   * @since 1.0.0
   * @author AFR
   */
  public double setPositionFeedback(int positionInEncoderCounts) {
    _updateSetPositions(positionInEncoderCounts);
    m_TelescopeMotor.set(TalonFXControlMode.MotionMagic, positionInEncoderCounts);
    return prevSetPosition;
  }

  /**
   * Stops the m_TelescopeMotor
   *
   * @version 1.0.0
   * @since 1.0.0
   * @author AFR
   */
  public void stop() {
    m_TelescopeMotor.set(TalonFXControlMode.PercentOutput, 0);
  }

  /**
   * Gets the current position of the m_TelescopeMotor in encoder counts
   *
   * @version 1.0.0
   * @since 1.0.0
   * @author AFR
   */
  public double getPosition() {
    return currentPosition;
  }

  @Override
  public void periodic() {
    currentPosition = m_TelescopeMotor.getSelectedSensorPosition();
  }

  /**
   * Updates the prevSetPosition and the newSetPosition
   *
   * @version 1.0.0
   * @since 1.0.0
   * @author AFR
   */
  private void _updateSetPositions(int newPosition) {
    prevSetPosition = newSetPosition;
    newSetPosition = newPosition;
  }
}
