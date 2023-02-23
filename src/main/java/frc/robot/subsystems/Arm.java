package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {

    public TalonFX shoulder = new TalonFX(0);
    public TalonFX elbow = new TalonFX(0);
    private DigitalInput ShoulderHome = new DigitalInput(0);
    private DigitalInput ElbowHome = new DigitalInput(0);
    private DigitalInput ElbowFloor = new DigitalInput(0);
    private DigitalInput ElbowLow = new DigitalInput(0);
    private DigitalInput ElbowHigh = new DigitalInput(0);
    private DigitalInput ShoulderHigh = new DigitalInput(0);

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
        //TODO set the value of the position variable to the input
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

    public boolean isShoulderHome(){
        return ShoulderHome.get();
    }

    public boolean isShoulderHigh(){
        return ShoulderHigh.get();
    }
    
}
