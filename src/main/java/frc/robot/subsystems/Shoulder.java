package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shoulder extends SubsystemBase {

    public WPI_TalonFX shoulder = new WPI_TalonFX(Constants.ShoulderMotorPort);
    private DigitalInput ShoulderMid = new DigitalInput(Constants.ShoulderMidLimitSwitchPort);
    private DigitalInput ShoulderLow = new DigitalInput(Constants.ShoulderLowLimitSwitchPort);
    private DigitalInput ShoulderHigh = new DigitalInput(Constants.ShoulderHighLimitSwitchPort);
    private DigitalInput ShoulderStart = new DigitalInput(Constants.ShoulderStartLimitSwitchPort);

    public boolean isShoulderMid(){
        return ShoulderMid.get();
    }

    public boolean isShoulderLow(){
        return ShoulderLow.get();
    }

    public boolean isShoulderHigh(){
        return ShoulderHigh.get();
    }

    public boolean isShoulderStart(){
        return ShoulderStart.get();
    }

    public void moveShoulder(double speed){
        shoulder.set(speed);
    }

    public double getEncoderPosition(){
        return shoulder.getSelectedSensorPosition();
        
    }
    
}
