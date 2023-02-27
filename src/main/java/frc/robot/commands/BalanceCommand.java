package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

public class BalanceCommand extends CommandBase {
  
    private Swerve m_Swerve;

    //private double error;
    private double pitchCurrentAngle;
    //private double rollCurrentAngle;
    //private double drivePower;
  
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
      this.pitchCurrentAngle = m_Swerve.getPitch();
      //this.rollCurrentAngle = m_Swerve.getRoll();

      if (Math.abs(pitchCurrentAngle) > 2){
        if (this.pitchCurrentAngle < 0) {
          m_Swerve.drive(new Translation2d(0.1, 0.0), 0.0, false, false);
        }
        else{
          m_Swerve.drive(new Translation2d(-0.1, 0.0), 0.0, false, false);
        }
      }

      /* f (Math.abs (rollCurrentAngle) > 2){
        if (this.rollCurrentAngle < 0){
          m_Swerve.drive(new Translation2d(0.0, -0.1), 0.0, false, false);
      } 
      else {
        m_Swerve.drive(new Translation2d(0.0, 0.1), 0.0, false, false);
      } */

      //&& Math.abs(rollCurrentAngle) <= 2

      if (Math.abs(pitchCurrentAngle) <= 2 ){
        m_Swerve.drive(new Translation2d(0.0, 0.0), 0.0, false, false);
      }
       // Debugging Print Statments
       SmartDashboard.putNumber("Current Pitch: ", pitchCurrentAngle);
    } 

     
      //SmartDashboard.putNumber("Current Roll: ", rollCurrentAngle);
    
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      //Raise the flag
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
      //return Math.abs(error) < 1; // End the command when we are within the specified threshold of being 'flat' (gyroscope pitch of 0 degrees)
    }  
}
