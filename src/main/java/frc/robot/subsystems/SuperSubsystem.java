package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

public class SuperSubsystem {
    
    public static class TalonFXConfigurationDefault {
        public NeutralMode neutralMode = NeutralMode.Brake;

        public double NEUTRAL_DEADBAND = 0.01;

        public boolean ENABLE_SOFT_LIMIT_FORWARD = false;
        public boolean ENABLE_SOFT_LIMIT_REVERSE = false;
        public int FORWARD_SOFT_LIMIT = 0;
        public int REVERSE_SOFT_LIMIT = 0;

        public SensorInitializationStrategy SENSOR_INITIALIZATION_STRATEGY =
            SensorInitializationStrategy.BootToZero;


        public int TIMEOUT = 0;

        public double SLOT0_KP = 0.0;
        public double SLOT0_KI = 0.0;
        public double SLOT0_KD = 0.0;
        public double SLOT0_KF = 0.0;
    }

    public static class CANCoderConfigurationDefault {
        
    }

}