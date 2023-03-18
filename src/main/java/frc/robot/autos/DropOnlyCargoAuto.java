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
import frc.robot.commands.MoveShoulderToHigh;
import frc.robot.commands.MoveRobotSimple;

public class DropOnlyCargoAuto extends SequentialCommandGroup {
    public DropOnlyCargoAuto(Swerve s_Swerve, Elbow m_elbow, Shoulder m_shoulder){
        addCommands(
            new InstantCommand(() -> s_Swerve.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)))),

            new ParallelCommandGroup(
            new MoveShoulderToHigh (m_shoulder, .4)
            ),

            new ParallelCommandGroup(
            new MoveShoulderToHome (m_shoulder, 0.2)
            
            )
        
        );
        
    }
}