package frc.robot.autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Shoulder;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.MoveShoulderLow;
import frc.robot.commands.MoveRobotSimple;
import frc.robot.commands.MoveShoulderHigh;

public class SideAutonomous extends SequentialCommandGroup {
    public SideAutonomous(Swerve s_Swerve, Arm m_elbow, Shoulder m_shoulder){
        addCommands(
            new InstantCommand(() -> s_Swerve.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)))),

            new ParallelCommandGroup(
                //new MoveElbowToMid (m_elbow, 0.2),
                //new MoveShoulderToMid (m_shoulder, 0.2)
                new MoveShoulderHigh (m_shoulder, 0.4)
            ),

            //new InstantCommand(() -> Elbow.raiseWrist()),
            //new InstantCommand(() -> Elbow.openFingers() ),

            new ParallelCommandGroup(
            //    new MoveElbowToHome (m_elbow, 0.2),
            //    new MoveShoulderToHome (m_shoulder, 0.2)
                new MoveShoulderLow (m_shoulder, 0.2)
            
            ), 
            new MoveRobotSimple(s_Swerve, -1.0, 0, 0).withTimeout(.5),
            new MoveRobotSimple(s_Swerve, 0, 0, 180).withTimeout(1.5),
            new MoveRobotSimple(s_Swerve, -1.0, 0, 0).withTimeout(5),
            new MoveRobotSimple(s_Swerve, 0, -1.0, 0).withTimeout(1)
            //new BalanceCommand(s_Swerve)
        );

        
    }
}