package frc.robot.autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Swerve;
import frc.robot.commands.MoveRobotSimple;

public class NoArmAutoSide1 extends SequentialCommandGroup {
    public NoArmAutoSide1(Swerve s_Swerve, Arm m_elbow, Shoulder m_shoulder){

        addCommands(
            new InstantCommand(() -> s_Swerve.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)))),

            //new MoveRobotSimple(s_Swerve, -1, 0, 0).withTimeout(.5),
            //new MoveRobotSimple(s_Swerve, 0, 0, 90).withTimeout(.77),
            new MoveRobotSimple(s_Swerve, 0, 0.5, 0).withTimeout(4.3),
            new MoveRobotSimple(s_Swerve, 0, -.5, 0).withTimeout(2.3),

            new MoveRobotSimple(s_Swerve, -.5, 0, 0).withTimeout(2.3)

            //new BalanceCommand(s_Swerve),
            //new BalanceCommand(s_Swerve),
            //new BalanceCommand(s_Swerve)
        );
    }
}