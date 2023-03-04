// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
//import frc.robot.subsystems.Arm.Position;
// May be needed soon when we actually have the arm to code

/** An example command that uses an example subsystem. */
public class MoveElbowToFloor extends CommandBase {
  private final Arm m_arm;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public MoveElbowToFloor(Arm subsystem) {
    m_arm = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_arm.elbow.set(ControlMode.Velocity, -0.5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_arm.elbow.set(ControlMode.Velocity, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_arm.isElbowFloor();
  }
}