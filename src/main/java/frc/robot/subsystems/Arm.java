package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {

    private TalonFX shoulder = new TalonFX(0);
    private TalonFX elbow = new TalonFX(0);

    public void Resting(){

    }

    public void Ground(){

    }
    
    public void low(){

    }

    public void high(){

    }

    
}
