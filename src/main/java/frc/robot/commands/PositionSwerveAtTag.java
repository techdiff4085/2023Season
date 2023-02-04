package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class PositionSwerveAtTag extends PIDCommand{

    public PositionSwerveAtTag(NetworkTableEntry ty, Swerve swerveSubsystem) {
        super(
            makePIDController(), 
            () -> ty.getDouble(0.0), 
            1, 
            (output) -> swerveSubsystem.drive(new Translation2d(output, 0.0), 0, false, false), 
            swerveSubsystem);
    }

    public static PIDController makePIDController(){
        PIDController controller = new PIDController(Constants.Swerve.driveKP, Constants.Swerve.driveKI, Constants.Swerve.driveKD);
        controller.setTolerance(0.05);
        return controller;
    }

    @Override
    public boolean isFinished() {
        return super.m_controller.atSetpoint();
    }
}
