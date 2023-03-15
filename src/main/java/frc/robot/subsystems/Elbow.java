package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elbow extends SubsystemBase {
    public WPI_TalonFX elbow = new WPI_TalonFX(Constants.ElbowMotorPort);
    private DigitalInput ElbowHome = new DigitalInput(Constants.ElbowHomeLimitSwitchPort);
    private DigitalInput ElbowFloor = new DigitalInput(Constants.ElbowFloorLimitSwitchPort);
    private DigitalInput ElbowLow = new DigitalInput(Constants.ElbowLowLimitSwitchPort);
    private DigitalInput ElbowHigh = new DigitalInput(Constants.ElbowHighLimitSwitchPort);
    private static Solenoid Wrist = new Solenoid(18, PneumaticsModuleType.CTREPCM, 6);
    private static Solenoid Fingers = new Solenoid(18, PneumaticsModuleType.CTREPCM, 4);

    public static LastLimitSwitch lastLimitSwitch = null;
    public enum LastLimitSwitch{
        HIGH,
        HOME,
        MID,
        FLOOR
    }

    public boolean isElbowHome(){
        return ElbowHome.get();
    }

    public boolean isElbowFloor(){
        return ElbowFloor.get();
    }

    public boolean isElbowLow(){
        return ElbowLow.get();
    }

    public boolean isElbowHigh(){
        return ElbowHigh.get();
    }

    public static void toggleGrabber(){
        Fingers.toggle();
    }

    public static void toggleWrist(){
        Wrist.toggle();
    }
    
    public static void raiseWrist(){
        Wrist.set(true);
    }

    public static void openFingers(){
        Fingers.set(true);
    }
}
