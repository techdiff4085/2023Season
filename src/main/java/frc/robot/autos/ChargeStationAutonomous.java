package frc.robot.autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.BalanceCommand;
import frc.robot.commands.ExtendArm;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Hand;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Swerve;
import frc.robot.commands.MoveRobotSimple;
import frc.robot.commands.MoveShoulderDeliverHigh;
import frc.robot.commands.MoveShoulderStart;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.RetractArm;

public class ChargeStationAutonomous extends SequentialCommandGroup {
    public ChargeStationAutonomous(Swerve s_Swerve, Hand m_Hand, Arm m_Arm, Shoulder m_shoulder, Spark light){

        addCommands(
            new InstantCommand(() -> m_Arm.zeroEncoderPosition()),
            new InstantCommand(() -> m_shoulder.zeroEncoderPosition()),
            new InstantCommand(() -> s_Swerve.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)))),
            new InstantCommand(() -> light.set(0.91)),//violet
            new ParallelCommandGroup(
                new OuttakeCommand(m_Hand, 0.4).withTimeout(1.5),
                new MoveShoulderDeliverHigh(m_shoulder, 0.8),
                new ExtendArm(m_Arm, 0.7)
            ),
            new OuttakeCommand(m_Hand, -0.4).withTimeout(0.5),
            new InstantCommand(() -> m_Hand.HandMotor.set(0)),
            new ParallelCommandGroup(
                new RetractArm(m_Arm, 0.7),
                new MoveShoulderStart(m_shoulder, 0.8),
                new SequentialCommandGroup(
                    new MoveRobotSimple(s_Swerve, -1, 0, 0).withTimeout(5.1),
                    new MoveRobotSimple(s_Swerve, 1, 0, 0).withTimeout(2.2),
                    new BalanceCommand(s_Swerve),
                    new BalanceCommand(s_Swerve),
                    new BalanceCommand(s_Swerve),
                    new InstantCommand(() -> light.set(0.21))//light chase
                )                
            )
            //new MoveRobotSimple(s_Swerve, 0, 0, 90).withTimeout(0.25),            
        );
    }
}