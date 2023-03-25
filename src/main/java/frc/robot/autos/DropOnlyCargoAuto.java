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
import frc.robot.commands.MoveShoulderHigh;


public class DropOnlyCargoAuto extends SequentialCommandGroup {
    public DropOnlyCargoAuto(Swerve s_Swerve, Arm m_elbow, Shoulder m_shoulder){
        addCommands(
            new InstantCommand(() -> s_Swerve.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)))),

            new ParallelCommandGroup(
            new MoveShoulderHigh (m_shoulder, .4)
            ),

            new ParallelCommandGroup(
            new MoveShoulderLow (m_shoulder, 0.2)
            
            )
        
        );
        
    }
}