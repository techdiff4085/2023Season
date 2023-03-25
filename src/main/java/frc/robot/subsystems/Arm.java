package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    public WPI_TalonFX Arm = new WPI_TalonFX(Constants.ElbowMotorPort);


    public boolean isArmRetracted(){
        return Arm.getSelectedSensorPosition() < Constants.armRetractedPosition;
    }

    public boolean isArmExtended(){
        return Arm.getSelectedSensorPosition() > 2000;
    }

    public void moveArm(double speed){
        Arm.set(speed);
    }
    public double getEncoderPosition(){
        return Arm.getSelectedSensorPosition();
    }
}
