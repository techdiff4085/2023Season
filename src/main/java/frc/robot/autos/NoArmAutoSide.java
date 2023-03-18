package frc.robot.autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Elbow;
import frc.robot.subsystems.Shoulder;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.MoveElbowToMid;
import frc.robot.commands.MoveShoulderToMid;
import frc.robot.commands.BalanceCommand;
import frc.robot.commands.MoveElbowToHome;
import frc.robot.commands.MoveShoulderToHome;
import frc.robot.commands.MoveRobotSimple;

public class NoArmAutoSide extends SequentialCommandGroup {
    public NoArmAutoSide(Swerve s_Swerve, Elbow m_elbow, Shoulder m_shoulder){
        addCommands(
            new InstantCommand(() -> s_Swerve.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)))),
            // don't use this. new MoveRobotSimple(s_Swerve, 1, 0, 0).withTimeout(5), // this goes forward
            // don't use this. testing only. new MoveRobotSimple(s_Swerve, 0, 1, 0).withTimeout(5)
            new MoveRobotSimple(s_Swerve, -1, 0, 0).withTimeout(5)
            //new MoveRobotSimple(s_Swerve, 0, 0, 90).withTimeout(.75),
            //new MoveRobotSimple(s_Swerve, 0, 1, 0).withTimeout(5),
            //new MoveRobotSimple(s_Swerve, 0, -1, 0).withTimeout(2.75),
            //new BalanceCommand(s_Swerve)
        );

        
    }
}