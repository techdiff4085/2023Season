package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;

/** An example command that uses an example subsystem. */
public class ExtendArm extends CommandBase {
  private final Arm m_Arm;
  private final double m_speed;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ExtendArm(Arm subsystem, double speed) {
    m_Arm = subsystem;
    m_speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_Arm.getEncoderPosition() > Constants.armExtendedPosition){
        m_Arm.Arm.set(-m_speed);
    }
    else if (m_Arm.getEncoderPosition() < Constants.armExtendedPosition){
        m_Arm.Arm.set(m_speed);
    }
    
   
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Arm.Arm.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_Arm.isArmExtended();
  }
}