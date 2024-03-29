/*
 * Initially from https://github.com/frc1678/C2022
 */

package frc.lib.team254.drivers;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;

@java.lang.SuppressWarnings({"java:S1104", "java:S116"})

/**
 * Creates CANTalon objects and configures all the parameters we care about to factory defaults.
 * Closed-loop and sensor parameters are not set, as these are expected to be set by the
 * application.
 */
public class TalonFXFactory {

  private static final int TIMEOUT_MS = 100;

  public static class Configuration {
    public NeutralMode NEUTRAL_MODE = NeutralMode.Coast;

    // factory default
    public double NEUTRAL_DEADBAND = 0.01; // FIXME: Tune down to 0.01 to remove chatter

    public boolean ENABLE_SOFT_LIMIT = false;
    public boolean ENABLE_LIMIT_SWITCH = false;
    public int FORWARD_SOFT_LIMIT = 0;
    public int REVERSE_SOFT_LIMIT = 0;

    public boolean INVERTED = false;
    public boolean SENSOR_PHASE = false;
    public SensorInitializationStrategy SENSOR_INITIALIZATION_STRATEGY =
        SensorInitializationStrategy.BootToZero;

    public int CONTROL_FRAME_PERIOD_MS = 20; // 10
    public int MOTION_CONTROL_FRAME_PERIOD_MS = 100;
    public int GENERAL_STATUS_FRAME_RATE_MS = 20; // 10
    public int FEEDBACK_STATUS_FRAME_RATE_MS = 100;
    public int QUAD_ENCODER_STATUS_FRAME_RATE_MS = 1000;
    public int ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS = 1000;
    public int PULSE_WIDTH_STATUS_FRAME_RATE_MS = 1000;

    public SensorVelocityMeasPeriod VELOCITY_MEASUREMENT_PERIOD =
        SensorVelocityMeasPeriod.Period_100Ms;
    public int VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW = 64;

    public StatorCurrentLimitConfiguration STATOR_CURRENT_LIMIT =
        new StatorCurrentLimitConfiguration(false, 300, 700, 1);
    public SupplyCurrentLimitConfiguration SUPPLY_CURRENT_LIMIT =
        new SupplyCurrentLimitConfiguration(false, 40, 100, 1);

    public double OPEN_LOOP_RAMP_RATE = 0.0;
    public double CLOSED_LOOP_RAMP_RATE = 0.0;

    public double SLOT0_KP = 0.0;
    public double SLOT0_KI = 0.0;
    public double SLOT0_KD = 0.0;
    public double SLOT0_KF = 0.0;
  }

  private static final Configuration kDefaultConfiguration = new Configuration();
  private static final Configuration kFollowerConfiguration = new Configuration();

  static {
    // This control frame value seems to need to be something reasonable to avoid the Talon's
    // LEDs behaving erratically. Potentially try to increase as much as possible.
    kFollowerConfiguration.CONTROL_FRAME_PERIOD_MS = 100;
    kFollowerConfiguration.MOTION_CONTROL_FRAME_PERIOD_MS = 1000;
    kFollowerConfiguration.GENERAL_STATUS_FRAME_RATE_MS = 1000;
    kFollowerConfiguration.FEEDBACK_STATUS_FRAME_RATE_MS = 1000;
    kFollowerConfiguration.QUAD_ENCODER_STATUS_FRAME_RATE_MS = 1000;
    kFollowerConfiguration.ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS = 1000;
    kFollowerConfiguration.PULSE_WIDTH_STATUS_FRAME_RATE_MS = 1000;
    kFollowerConfiguration.ENABLE_SOFT_LIMIT = false;
  }

  // create a CANTalon with the default (out of the box) configuration
  public static TalonFX createDefaultTalon(int id, String canBusName) {
    return createTalon(id, canBusName, kDefaultConfiguration);
  }

  public static TalonFX createPermanentFollowerTalon(int id, String canBusName, int leaderID) {
    final TalonFX talon = createTalon(id, canBusName, kFollowerConfiguration);
    talon.set(ControlMode.Follower, leaderID);
    return talon;
  }

  public static TalonFX createTalon(int id, String canBusName, Configuration config) {
    TalonFX talon = new TalonFX(id, canBusName);
    TalonFXConfiguration talonFXConfig = new TalonFXConfiguration();

    talon.configFactoryDefault();

    talon.set(ControlMode.PercentOutput, 0.0);

    talonFXConfig.forwardLimitSwitchSource = LimitSwitchSource.FeedbackConnector;
    talonFXConfig.forwardLimitSwitchNormal = LimitSwitchNormal.Disabled;
    talonFXConfig.reverseLimitSwitchSource = LimitSwitchSource.FeedbackConnector;
    talonFXConfig.reverseLimitSwitchNormal = LimitSwitchNormal.Disabled;

    talonFXConfig.clearPositionOnLimitF = false;
    talonFXConfig.clearPositionOnLimitR = false;

    talonFXConfig.nominalOutputForward = 0.0;
    talonFXConfig.nominalOutputReverse = 0.0;
    talonFXConfig.neutralDeadband = config.NEUTRAL_DEADBAND;

    talonFXConfig.peakOutputForward = 1.0;
    talonFXConfig.peakOutputReverse = -1.0;

    talonFXConfig.forwardSoftLimitThreshold = config.FORWARD_SOFT_LIMIT;
    talonFXConfig.forwardSoftLimitEnable = config.ENABLE_SOFT_LIMIT;
    talonFXConfig.reverseSoftLimitThreshold = config.REVERSE_SOFT_LIMIT;
    talonFXConfig.reverseSoftLimitEnable = config.ENABLE_SOFT_LIMIT;

    talonFXConfig.initializationStrategy = config.SENSOR_INITIALIZATION_STRATEGY;

    talonFXConfig.velocityMeasurementPeriod = config.VELOCITY_MEASUREMENT_PERIOD;
    talonFXConfig.velocityMeasurementWindow = config.VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW;

    talonFXConfig.openloopRamp = config.OPEN_LOOP_RAMP_RATE;
    talonFXConfig.closedloopRamp = config.CLOSED_LOOP_RAMP_RATE;

    talonFXConfig.voltageCompSaturation = 0.0;
    talonFXConfig.voltageMeasurementFilter = 32;

    talonFXConfig.statorCurrLimit = config.STATOR_CURRENT_LIMIT;
    talonFXConfig.supplyCurrLimit = config.SUPPLY_CURRENT_LIMIT;

    talonFXConfig.slot0.kP = config.SLOT0_KP;
    talonFXConfig.slot0.kI = config.SLOT0_KI;
    talonFXConfig.slot0.kD = config.SLOT0_KD;
    talonFXConfig.slot0.kF = config.SLOT0_KF;

    talon.configAllSettings(talonFXConfig, TIMEOUT_MS);

    talon.changeMotionControlFramePeriod(config.MOTION_CONTROL_FRAME_PERIOD_MS);
    talon.clearMotionProfileHasUnderrun(TIMEOUT_MS);
    talon.clearMotionProfileTrajectories();

    talon.clearStickyFaults(TIMEOUT_MS);

    talon.overrideLimitSwitchesEnable(config.ENABLE_LIMIT_SWITCH);

    talon.setNeutralMode(config.NEUTRAL_MODE);

    talon.overrideSoftLimitsEnable(config.ENABLE_SOFT_LIMIT);

    talon.setInverted(config.INVERTED);
    talon.setSensorPhase(config.SENSOR_PHASE);

    talon.selectProfileSlot(0, 0);

    talon.enableVoltageCompensation(false);

    talon.setStatusFramePeriod(
        StatusFrameEnhanced.Status_1_General, config.GENERAL_STATUS_FRAME_RATE_MS, TIMEOUT_MS);
    talon.setStatusFramePeriod(
        StatusFrameEnhanced.Status_2_Feedback0, config.FEEDBACK_STATUS_FRAME_RATE_MS, TIMEOUT_MS);
    talon.setStatusFramePeriod(
        StatusFrameEnhanced.Status_3_Quadrature,
        config.QUAD_ENCODER_STATUS_FRAME_RATE_MS,
        TIMEOUT_MS);
    talon.setStatusFramePeriod(
        StatusFrameEnhanced.Status_4_AinTempVbat,
        config.ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS,
        TIMEOUT_MS);
    talon.setStatusFramePeriod(
        StatusFrameEnhanced.Status_8_PulseWidth,
        config.PULSE_WIDTH_STATUS_FRAME_RATE_MS,
        TIMEOUT_MS);

    talon.setControlFramePeriod(ControlFrame.Control_3_General, config.CONTROL_FRAME_PERIOD_MS);

    return talon;
  }

  private TalonFXFactory() {}
}
