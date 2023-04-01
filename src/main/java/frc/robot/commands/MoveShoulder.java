// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shoulder;

/** An example command that uses an example subsystem. */
public class MoveShoulder extends CommandBase {
  private final Shoulder m_shoulder;
  private final Joystick m_joystick;
  private final int m_axis;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public MoveShoulder (Shoulder subsystem, Joystick controller, int axis) {
    m_shoulder = subsystem;
    m_joystick = controller;
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
    SmartDashboard.putNumber("Shoulder Encoder", m_shoulder.getEncoderPosition());
    m_shoulder.shoulder.set(m_joystick.getRawAxis(m_axis)/2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shoulder.shoulder.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}