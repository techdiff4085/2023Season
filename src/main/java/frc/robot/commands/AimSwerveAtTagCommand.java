package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class AimSwerveAtTagCommand extends PIDCommand{

    public AimSwerveAtTagCommand(NetworkTableEntry tx, Double setpoint, Swerve swerveSubsystem) {
        super(makePIDController(), 
        () -> tx.getDouble(0.0), 
        setpoint, 
        (output) -> swerveSubsystem.drive(new Translation2d(0, output), 0, false, false), 
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
