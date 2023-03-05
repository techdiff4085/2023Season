package frc.robot.autos;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class SideAutonomous extends SequentialCommandGroup {
    public SideAutonomous(Swerve s_Swerve){
       
        TrajectoryConfig config =
            new TrajectoryConfig(
                    //Good Speed is 8 
                    8,
                    Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                .setKinematics(Constants.Swerve.swerveKinematics);

        // An example trajectory to follow.  All units in meters.
        Trajectory exampleTrajectory1 =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                // 4 should be 18.8
                
                List.of(
                    new Translation2d(17, Units.feetToMeters(0)),
                    new Translation2d(8, Units.feetToMeters(1))

                ), 
                // End 3 meters straight ahead of where we started, facing forward
                // good feetToMeters is 29 to get on charge station
                new Pose2d(-12, Units.feetToMeters(0), new Rotation2d(0)),
                config);

        /* Trajectory exampleTrajectory2 =
                TrajectoryGenerator.generateTrajectory(
                    // Start at the origin facing the +X direction
                    new Pose2d(0, 0, new Rotation2d(0)),
                    // Pass through these two interior waypoints, making an 's' curve path
                    // 4 should be 18.8
                    
                    List.of(
                        new Translation2d(Units.feetToMeters(0), Units.feetToMeters(-4))
                ), 
                    // End 3 meters straight ahead of where we started, facing forward
                    // good feetToMeters is 29 to get on charge station
                    new Pose2d(Units.feetToMeters(0), Units.feetToMeters(-8), new Rotation2d(0)),
                    config);               */ 
           
        var thetaController =
            new ProfiledPIDController(
                Constants.AutoConstants.kPThetaController, 0, 0, Constants.AutoConstants.kThetaControllerConstraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        SwerveControllerCommand swerveControllerCommand =
            new SwerveControllerCommand(
                exampleTrajectory1,
                s_Swerve::getPose,
                Constants.Swerve.swerveKinematics,
                new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                thetaController,
                s_Swerve::setModuleStates,
                s_Swerve);


        addCommands(
            new InstantCommand(() -> s_Swerve.resetOdometry(exampleTrajectory1.getInitialPose())),
            swerveControllerCommand
        );

        
    }
}