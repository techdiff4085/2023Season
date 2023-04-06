package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    public WPI_TalonFX Arm = new WPI_TalonFX(Constants.ElbowMotorPort);
    TalonFXSensorCollection sensor = new TalonFXSensorCollection(Arm);

    public Arm(){
        Arm.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    }

    public boolean isArmRetracted(){
        return sensor.getIntegratedSensorPosition() >= Constants.armRetractedPosition;
    }

    public boolean isArmExtended(){
        return sensor.getIntegratedSensorPosition() <= Constants.armExtendedPosition;
    }

    public void moveArm(double speed){
        Arm.set(speed);
    }
    public double getEncoderPosition(){
        SmartDashboard.putNumber("Arm Encoder", sensor.getIntegratedSensorPosition());
        return sensor.getIntegratedSensorPosition();
    }

    public void zeroEncoderPosition(){
        sensor.setIntegratedSensorPosition(0, 0);    
    }

}
