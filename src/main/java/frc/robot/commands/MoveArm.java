package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;

/** An example command that uses an example subsystem. */
public class MoveArm extends CommandBase {
  private final Arm m_Arm;
  private final Joystick m_controller;
  private final int m_axis;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public MoveArm(Arm subsystem, Joystick controller, int axis) {
    m_Arm = subsystem;
    m_controller = controller;
    m_axis = axis;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("Arm Encoder", m_Arm.getEncoderPosition());
    double speed = m_controller.getRawAxis(m_axis);
    if(speed > 0){ //we are retracting
      if (m_Arm.getEncoderPosition() >= Constants.armRetractedPosition){
        m_Arm.Arm.set(0);  
      } else {
        m_Arm.Arm.set(speed); 
      }
    } else if (speed < 0){
      if (m_Arm.getEncoderPosition() < Constants.armExtendedPosition){
        m_Arm.Arm.set(0);  
      } else {
        m_Arm.Arm.set(speed); 
      }      
    } else {
      m_Arm.Arm.set(0);
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
    return false;
  }
}