package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.lib.util.COTSFalconSwerveConstants;
import frc.lib.util.SwerveModuleConstants;

public final class Constants {
    public static final double stickDeadband = 0.1;

    public static double slowSpeed = 2.0;
    public static double fastSpeed = 1.25;

    //TODO update all port numbers
    public static final int DriverJoystickPort = 0;
    public static final int ArmDriverJoystickPort = 1;

    /* Arm Motors */
    public static final int ShoulderMotorPort = 27;
    public static final int ElbowMotorPort = 32;
    
    /* Limit Switches */
    public static final int ShoulderHomeLimitSwitchPort = 6;  //shoulder stop
    //public static final int ElbowHomeLimitSwitchPort = 2;  //#2
    //public static final int ElbowFloorLimitSwitchPort = 7; //#arm stop #7
    //public static final int ElbowLowLimitSwitchPort = 1;  //#1
    //public static final int ElbowHighLimitSwitchPort = 3; //#4
    public static final int ShoulderHighLimitSwitchPort = 4; //#3
    public static final int ShoulderLowLimitSwitchPort = 5;

    /* Arm Encoder position */
    public static final double armRetractedPosition = 0;
    public static final double almostRetracted = -60000;
    public static final double almostExtended = -430000;
    public static final double armExtendedPosition = -465377;
    public static final double shoulderStartPosition = 0;
    public static final double shoulderHighPosition = 137839;
    public static final double shoulderDeliverHighPosition = 262018;
    public static final double shoulderMidPosition = 255751; 
    public static final double almostLow = 400000;
    public static final double shoulderLowPosition = 443684;

    /* Lidar */
    public static final int LidarPort = 0;

    public static final int ShoulderStartLimitSwitchPort = 1;

    public static final int ShoulderMidLimitSwitchPort = 2;

    public static final int HandMotor = 17;

    public static final class Swerve {
        
        public static final int pigeonID = 20;
        public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-


        

        public static final COTSFalconSwerveConstants chosenModule =  //This must be tuned to specific robot
            COTSFalconSwerveConstants.SDSMK4i(COTSFalconSwerveConstants.driveGearRatios.SDSMK4i_L1);

        /* Drivetrain Constants */
        //TODO - update measurements
        public static final double trackWidth = Units.inchesToMeters(24.0); //This must be tuned to specific robot
        public static final double wheelBase = Units.inchesToMeters(24.0); //This must be tuned to specific robot
        public static final double wheelCircumference = chosenModule.wheelCircumference;

        /* Swerve Kinematics 
         * No need to ever change this unless you are not doing a traditional rectangular/square 4 module swerve */
         public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
            new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0));

        /* Module Gear Ratios */
        public static final double driveGearRatio = chosenModule.driveGearRatio;
        public static final double angleGearRatio = chosenModule.angleGearRatio;

        /* Motor Inverts */
        public static final boolean angleMotorInvert = chosenModule.angleMotorInvert;
        public static final boolean driveMotorInvert = chosenModule.driveMotorInvert;

        /* Angle Encoder Invert */
        public static final boolean canCoderInvert = chosenModule.canCoderInvert;

        /* Swerve Current Limiting */
        public static final int angleContinuousCurrentLimit = 25;
        public static final int anglePeakCurrentLimit = 40;
        public static final double anglePeakCurrentDuration = 0.1;
        public static final boolean angleEnableCurrentLimit = true;

        public static final int driveContinuousCurrentLimit = 35;
        public static final int drivePeakCurrentLimit = 60;
        public static final double drivePeakCurrentDuration = 0.1;
        public static final boolean driveEnableCurrentLimit = true;

        /* These values are used by the drive falcon to ramp in open loop and closed loop driving.
         * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc */
        public static final double openLoopRamp = 0.25;
        public static final double closedLoopRamp = 0.0;

        /* Angle Motor PID Values */
        public static final double angleKP = chosenModule.angleKP;
        public static final double angleKI = chosenModule.angleKI;
        public static final double angleKD = chosenModule.angleKD;
        public static final double angleKF = chosenModule.angleKF;

        /* Drive Motor PID Values */
        public static final double driveKP = 0.1; //This must be tuned to specific robot
        public static final double driveKI = 0.0;
        public static final double driveKD = 0.0;
        public static final double driveKF = 0.0;

        public static final double balanceDriveKP = 0.015;

        /* Drive Motor Characterization Values 
         * Divide SYSID values by 12 to convert from volts to percent output for CTRE */
        /* https://docs.wpilib.org/en/stable/docs/software/advanced-controls/controllers/feedforward.html
        The passed-in gains must have units consistent with the distance units, 
        or a compile-time error will be thrown. 
        kS should have units of volts, 
        kV should have units of volts * seconds / distance, and 
        kA should have units of volts * seconds^2 / distance */
        public static final double driveKS = (0.4 / 12); //This must be tuned to specific robot
        public static final double driveKV = (1.51 / 12);
        public static final double driveKA = (0.27 / 12);

        /* Swerve Profiling Values */
        /** Meters per Second */
        public static final double maxSpeed = 1; //This must be tuned to specific robot
        /** Radians per Second */
        public static final double maxAngularVelocity = 1; //This must be tuned to specific robot

        /* Neutral Modes */
        public static final NeutralMode angleNeutralMode = NeutralMode.Coast;
        public static final NeutralMode driveNeutralMode = NeutralMode.Brake;

        /* Module Specific Constants */
        /* Front Left Module - Module 0 */
        //TODO Verify all angleoffset numbers
        public static final class Mod0 { //This must be tuned to specific robot
            public static final int driveMotorID = 31;
            public static final int angleMotorID = 33;
            public static final int canCoderID = 36;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(2.3);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

        /* Front Right Module - Module 1 */
        public static final class Mod1 { //This must be tuned to specific robot
            public static final int driveMotorID = 24;
            public static final int angleMotorID = 26;
            public static final int canCoderID = 22;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(326.0);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }
        
        /* Back Left Module - Module 2 */
        public static final class Mod2 { //This must be tuned to specific robot
            public static final int driveMotorID = 29;
            public static final int angleMotorID = 35; 
            public static final int canCoderID = 0;  
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(211.81);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

        /* Back Right Module - Module 3 */
        public static final class Mod3 { //This must be tuned to specific robot
            public static final int driveMotorID = 25;
            public static final int angleMotorID = 23;
            public static final int canCoderID = 21;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(47.19);
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }
    }

    public static final class AutoConstants { //The below constants are used in the example auto, and must be tuned to specific robot
        public static final double kMaxSpeedMetersPerSecond = 6;
        public static final double kMaxAccelerationMetersPerSecondSquared = 6;
        public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
    
        public static final double kPXController = 1;// was 1
        public static final double kPYController = 1;//was 1
        public static final double kPThetaController = 1;
    
        /* Constraint for the motion profilied robot angle controller */
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
            new TrapezoidProfile.Constraints(
                kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
    }
}
