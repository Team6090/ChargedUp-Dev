package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drivetrain.DrivetrainConstants;
import frc.robot.subsystems.pixy.PixySystem;

public class IntakeSystem extends SubsystemBase {
    
    VictorSP intakeMotor;
    Solenoid intakeSolenoid;

    PixySystem pixySystem;

    boolean isIntakeSolenoidEnabled;

    public IntakeSystem(){
        intakeMotor = new VictorSP(DrivetrainConstants.IntakeMotor);
        intakeSolenoid = new Solenoid(DrivetrainConstants.PCMModule, PneumaticsModuleType.CTREPCM, DrivetrainConstants.IntakeSolenoid);
        pixySystem = new PixySystem();
    }

    public void EnableIntakeSolenoid(boolean enable){
        intakeSolenoid.set(enable);
        isIntakeSolenoidEnabled = enable;
    }

    public void EnableIntakeMotor(boolean enable, boolean isReverse){
        if (enable){
            if (!isReverse){
                intakeMotor.set(0.2);
            }else{
                intakeMotor.set(-0.2);
            }
        }else{
            intakeMotor.set(0);
        }
    }

    public boolean DetectedObject(){
        if (pixySystem.ObjectCount() > 0 ) { return true; }
        return false;
    }

    public int GetCurrentObjectSig(){
        return pixySystem.GetObjectSig();
    }

    @Override
    public void periodic(){

    }

}
