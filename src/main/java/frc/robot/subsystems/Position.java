package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Position  {

    private static ArmPosition position;
    
    public enum ArmPosition {
        Home,
        Floor,
        Low,
        High,
    }

    public static ArmPosition getPosition(){
        return position;
    }

    public static void setPosition(ArmPosition myPosition){
        position = myPosition;
        SmartDashboard.putString("Position", position.toString());
    }
}
