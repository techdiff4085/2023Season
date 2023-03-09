package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shoulder extends SubsystemBase {

    public TalonFX shoulder = new TalonFX(Constants.ShoulderMotorPort);
    private DigitalInput ShoulderHome = new DigitalInput(Constants.ShoulderHomeLimitSwitchPort);
    private DigitalInput ShoulderHigh = new DigitalInput(Constants.ShoulderHighLimitSwitchPort);

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

    public boolean isShoulderHome(){
        return ShoulderHome.get();
    }

    public boolean isShoulderHigh(){
        return ShoulderHigh.get();
    }
    
}
