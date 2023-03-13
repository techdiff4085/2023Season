package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shoulder extends SubsystemBase {

    public WPI_TalonFX shoulder = new WPI_TalonFX(Constants.ShoulderMotorPort);
    private DigitalInput ShoulderHome = new DigitalInput(Constants.ShoulderHomeLimitSwitchPort);
    private DigitalInput ShoulderLow = new DigitalInput(Constants.ShoulderLowLimitSwitchPort);
    private DigitalInput ShoulderHigh = new DigitalInput(Constants.ShoulderHighLimitSwitchPort);

    public boolean isShoulderHome(){
        return ShoulderHome.get();
    }

    public boolean isShoulderLow(){
        return ShoulderLow.get();
    }

    public boolean isShoulderHigh(){
        return ShoulderHigh.get();
    }
    
}
