package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Hand;

/** An example command that uses an example subsystem. */
public class OuttakeCommand extends CommandBase {
  private final Hand m_Hand;
  private double m_speed;
  
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public OuttakeCommand(Hand subsystem, double speed) {
    m_Hand = subsystem;  
    m_speed = speed;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_Hand.HandMotor.getMotorOutputVoltage() > 10){
      m_Hand.HandMotor.neutralOutput();
    } else {
      m_Hand.HandMotor.set(m_speed);
    }
    SmartDashboard.putNumber("Hand Voltage", m_Hand.HandMotor.getMotorOutputVoltage());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}