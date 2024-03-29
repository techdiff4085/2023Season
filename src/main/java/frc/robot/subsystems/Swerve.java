package frc.robot.subsystems;

import frc.robot.SwerveModule;
import frc.robot.Constants;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

//import com.ctre.phoenix.sensors.Pigeon2Configuration;
import com.ctre.phoenix.sensors.WPI_Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase {
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] mSwerveMods;
    public WPI_Pigeon2 gyro;

    public Swerve() {
        //gyro = new Pigeon2(Constants.Swerve.pigeonID,"Carnie");
        gyro = new WPI_Pigeon2(Constants.Swerve.pigeonID);

        mSwerveMods = new SwerveModule[] {
            new SwerveModule(0, Constants.Swerve.Mod0.constants),
            new SwerveModule(1, Constants.Swerve.Mod1.constants),
            new SwerveModule(2, Constants.Swerve.Mod2.constants),
            new SwerveModule(3, Constants.Swerve.Mod3.constants)
        };

        /* By pausing init for a second before setting module offsets, we avoid a bug with inverting motors.
         * See https://github.com/Team364/BaseFalconSwerve/issues/8 for more info.
         */
        Timer.delay(1.0);
        zeroGyro();

        swerveOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, getYaw(), getModulePositions());
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        SwerveModuleState[] swerveModuleStates =
            Constants.Swerve.swerveKinematics.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation, 
                                    getYaw()
                                )
                                : new ChassisSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation)
                                );
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }    

    /* Used by SwerveControllerCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);
        
        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
            //desiredStates[mod.moduleNumber].speedMetersPerSecond = Math.abs(desiredStates[mod.moduleNumber].getDriveVelocity());   
        }
        //dkt - test this out after 2/11
        //desiredStates[0].speedMetersPerSecond = Math.abs(m_frontLeftModule.getDriveVelocity());
        //desiredStates[1].speedMetersPerSecond = Math.abs(m_frontRightModule.getDriveVelocity());
        //desiredStates[2].speedMetersPerSecond = Math.abs(m_backLeftModule.getDriveVelocity());
        //desiredStates[3].speedMetersPerSecond = Math.abs(m_backRightModule.getDriveVelocity());
        //swerveOdometry.update(getGyroscopeRotation(), desiredStates);
        //https://github.com/5804/rapidReact2022Alpha/tree/f919742a8a5a3f2b0531bf845e8df870fbbffc71
        //https://www.chiefdelphi.com/t/autonomous-consistency-with-swerve-drive/403549
    }    

    public Pose2d getPose() {
        Pose2d pose = swerveOdometry.getPoseMeters();
        //SmartDashboard.putString("pose", pose.toString());
        return pose;
    }

    public void resetOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(getYaw(), getModulePositions(), pose);
    }

    public SwerveModuleState[] getModuleStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : mSwerveMods){
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    public SwerveModulePosition[] getModulePositions(){
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for(SwerveModule mod : mSwerveMods){
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    public void zeroGyro(){
        gyro.setYaw(0);
        resetModulesToAbsolute();
    }

    public double getPitch(){
        return gyro.getPitch();
    }

    public double getRoll(){
        return gyro.getRoll();
    }

    public Rotation2d getYaw() {
        return (Constants.Swerve.invertGyro) ? Rotation2d.fromDegrees(360 - gyro.getYaw()) : Rotation2d.fromDegrees(gyro.getYaw());
    }

    public void resetModulesToAbsolute(){
        for(SwerveModule mod : mSwerveMods){
            mod.resetToAbsolute();
        }
    }
    public void resetPose(Pose2d pose) {
        
        //m_poseEstimator.resetPosition(getYaw(), getModulePositions(), pose);
    }
    
    @Override
    public void periodic(){
        swerveOdometry.update(getYaw(), getModulePositions());  

        for(SwerveModule mod : mSwerveMods){
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Cancoder", mod.getCanCoder().getDegrees());
            /*
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Integrated", mod.getPosition().angle.getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond); 
            SmartDashboard.putString("Mod" + mod.moduleNumber + "Sticky Fault", mod.getStickyFault());  
            SmartDashboard.putNumber("Mod" + mod.moduleNumber + " getSelectedSensorPosition is", mod.getSelectedSensorPosition());
            SmartDashboard.putNumber("Mod" + mod.moduleNumber + " drive motor temperature is", mod.getDriveMotorTemperature());
            SmartDashboard.putNumber("Mod" + mod.moduleNumber + " angle motor temperature is", mod.getAngleMotorTemperature());
            */
        }
    }

    public void resetMotorPosition(){
        for(SwerveModule mod: mSwerveMods){
            mod.mDriveMotor.setSelectedSensorPosition(0);
        }
    }
}