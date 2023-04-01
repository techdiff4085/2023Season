package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shoulder extends SubsystemBase {

    public WPI_TalonFX shoulder = new WPI_TalonFX(Constants.ShoulderMotorPort);
    TalonFXSensorCollection sensor = new TalonFXSensorCollection(shoulder);
    
    private DigitalInput ShoulderMid = new DigitalInput(Constants.ShoulderMidLimitSwitchPort);
    private DigitalInput ShoulderHigh = new DigitalInput(Constants.ShoulderStartLimitSwitchPort);

    public Shoulder(){
        shoulder.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    }

    public boolean isShoulderMid(){
        return ShoulderMid.get();
    }

    public boolean isShoulderHigh(){
        return ShoulderHigh.get();
    }

    public void moveShoulder(double speed){
        shoulder.set(speed);
    }

    public double getEncoderPosition(){
        SmartDashboard.putNumber("Shoulder Encoder", sensor.getIntegratedSensorPosition());
        return sensor.getIntegratedSensorPosition();
        
    }

    public void zeroEncoderPosition(){
        sensor.setIntegratedSensorPosition(0, 0);
    }
    
}
