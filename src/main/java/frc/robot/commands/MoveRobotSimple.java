package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.math.geometry.Translation2d;


public class MoveRobotSimple extends CommandBase {
  private double m_x;
  private double m_y;
  private double m_rotation;
  private Swerve m_swerve;

  public MoveRobotSimple(Swerve swerve, double x, double y, double rotation){
      m_x = x;
      m_y = y;
      m_rotation = rotation;
      m_swerve = swerve;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}
    
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_swerve.drive(new Translation2d(m_x, m_y), m_rotation, false, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_swerve.drive(new Translation2d(0, 0), 0, false, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
