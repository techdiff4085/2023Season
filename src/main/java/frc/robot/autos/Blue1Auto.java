// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants;
import frc.robot.subsystems.*;

public class Blue1Auto extends SequentialCommandGroup {

  public Blue1Auto(Swerve swerve) {
    //Exclude ".path" from pathName
    PathPlannerTrajectory trajectory = PathPlanner.loadPath("Blue1Auto", AutoConstants.kMaxSpeedMetersPerSecond,
        AutoConstants.kMaxAccelerationMetersPerSecondSquared);
    SmartDashboard.putString("Trajectory is ", trajectory.toString());
    PPSwerveControllerCommand swerveControllerCommand = new PPSwerveControllerCommand(
        trajectory,
        swerve::getPose,
        Constants.Swerve.swerveKinematics,
        new PIDController(AutoConstants.kPXController, 0.15, 0),
        new PIDController(AutoConstants.kPYController, 0.15, 0),
        new PIDController(AutoConstants.kPThetaController, 0, 0),
        swerve::setModuleStates,
        true,
        swerve);

   //swerve.setFieldTrajectory("Trajectory", trajectory);

    // change the lambda to an external command or state it outside the runOnce function
    addCommands(new InstantCommand(() -> {
      swerve.resetOdometry(trajectory.getInitialHolonomicPose());
    }, swerve), new InstantCommand(() -> {
      swerve.resetPose(trajectory.getInitialHolonomicPose());
    }, swerve),
        swerveControllerCommand,
        new InstantCommand(() -> {
          SmartDashboard.putString("pose", swerve.getPose().toString());
        } ));
  }
}