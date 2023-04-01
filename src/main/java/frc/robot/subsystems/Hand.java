package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;



public class Hand extends SubsystemBase{
    public WPI_TalonFX HandMotor = new WPI_TalonFX(Constants.HandMotor);
    public static Solenoid Wrist = new Solenoid(18, PneumaticsModuleType.CTREPCM, 6);

    public Hand(){
        HandMotor.setNeutralMode(NeutralMode.Brake);
    }
    
}
