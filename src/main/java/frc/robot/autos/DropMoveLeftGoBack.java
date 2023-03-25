package frc.robot.autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Hand;
import frc.robot.subsystems.Shoulder;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.MoveShoulderStart;
import frc.robot.commands.MoveRobotSimple;

public class DropMoveLeftGoBack extends SequentialCommandGroup {
    public DropMoveLeftGoBack(Swerve s_Swerve, Hand Hand, Arm m_Arm, Shoulder m_shoulder){
        addCommands(
            new InstantCommand(() -> s_Swerve.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)))),

            new ParallelCommandGroup(
                new MoveShoulderStart (m_shoulder, 4),
                new InstantCommand(() -> frc.robot.subsystems.Hand.Wrist.set(false)),
                new InstantCommand(() -> m_Arm.Arm.set(1))),

            // move left here before moving backwards
            new MoveRobotSimple(s_Swerve, 0, 1, 0).withTimeout(2.5),


            //new MoveRobotSimple(s_Swerve, -1, 0, 0).withTimeout(.5),

            // if the robot doesn't move 180, then remove the next line. 
            new MoveRobotSimple(s_Swerve, 0, 0, 180).withTimeout(1.5),
            
            new MoveRobotSimple(s_Swerve, -1, 0, 0).withTimeout(4.5)
        );

        
    }
}