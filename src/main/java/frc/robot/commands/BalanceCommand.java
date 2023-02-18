package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class BalanceCommand extends CommandBase {
  
    private Swerve m_Swerve;

    private double error;
    private double currentAngle;
    private double drivePower;
  
    public BalanceCommand(Swerve swerve) {
      this.m_Swerve = swerve;
      addRequirements(m_Swerve);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      this.currentAngle = m_Swerve.getPitch();

      SmartDashboard.putNumber("Pitch", currentAngle);
  
      error = -currentAngle;
      drivePower = -Math.min(Constants.Swerve.balanceDriveKP * error, 1);
  
      /* 
      // Our robot needed an extra push to drive up in reverse, probably due to weight imbalances
      if (drivePower < 0) {
        drivePower *= Constants.BACKWARDS_BALANCING_EXTRA_POWER_MULTIPLIER;
      }
  */
      // Limit the max power
      if (Math.abs(drivePower) > 0.4) {
        drivePower = Math.copySign(0.4, drivePower);
      }
  
      m_Swerve.drive(new Translation2d(0.00, 0.001), 0.0, false, false);
      
      // Debugging Print Statments
      System.out.println("Current Angle: " + currentAngle);
      System.out.println("Error " + error);
      System.out.println("Drive Power: " + drivePower);
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      //m_Swerve.drive(new Translation2d(0.0, 0.0), 0.0, false, false);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return Math.abs(error) < 1; // End the command when we are within the specified threshold of being 'flat' (gyroscope pitch of 0 degrees)
    }  
}
