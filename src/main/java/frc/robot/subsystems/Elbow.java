package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elbow extends SubsystemBase {

    public TalonFX shoulder = new TalonFX(Constants.ShoulderMotorPort);
    public TalonFX elbow = new TalonFX(Constants.ElbowMotorPort);
    private DigitalInput ElbowHome = new DigitalInput(Constants.ElbowHomeLimitSwitchPort);
    private DigitalInput ElbowFloor = new DigitalInput(Constants.ElbowFloorLimitSwitchPort);
    private DigitalInput ElbowLow = new DigitalInput(Constants.ElbowLowLimitSwitchPort);
    private DigitalInput ElbowHigh = new DigitalInput(Constants.ElbowHighLimitSwitchPort);
    private static Solenoid Wrist = new Solenoid(18, PneumaticsModuleType.CTREPCM, 6);
    private static Solenoid Fingers = new Solenoid(18, PneumaticsModuleType.CTREPCM, 4);


    public enum Position {
        Home,
        Floor,
        Low,
        High,
    }

    private Position position = Position.Home;

    public Position getPosition(){
        return position;
    }

    public void setPosition(Position myPosition){
        myPosition = position;
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
    
}
