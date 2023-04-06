package frc.robot.autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Hand;
import frc.robot.subsystems.Shoulder;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.MoveShoulderLow;
import frc.robot.commands.MoveShoulderStart;
import frc.robot.commands.ExtendArm;
import frc.robot.commands.MoveRobotSimple;
import frc.robot.commands.MoveShoulderDeliverHigh;
import frc.robot.commands.MoveShoulderHigh;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.RetractArm;

public class DropCube extends SequentialCommandGroup {
    public DropCube(Swerve s_Swerve, Hand m_Hand, Arm m_Arm, Shoulder m_shoulder){
        addCommands(
            new InstantCommand(() -> s_Swerve.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)))),

                new OuttakeCommand(m_Hand, -0.4).withTimeout(1.5), 
                
                new WaitCommand(2),
            
                new SequentialCommandGroup(
                    new MoveRobotSimple(s_Swerve, -1, 0, 0).withTimeout(5)
                ), 
                new InstantCommand(() -> Hand.Wrist.toggle())
            
            //new BalanceCommand(s_Swerve)
        );

        
    }
}