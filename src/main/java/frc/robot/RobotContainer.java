package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton aimRobotAtTag = new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton positionRobotAtTag = new JoystickButton(driver, XboxController.Button.kB.value);

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();

    /* Limelight */
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private static NetworkTableEntry ty = table.getEntry("ty");
    private static NetworkTableEntry tx = table.getEntry("tx");


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis)/2, 
                () -> -driver.getRawAxis(strafeAxis)/2, 
                () -> -driver.getRawAxis(rotationAxis)/2, 
                () -> robotCentric.getAsBoolean()
            )
        );

        // Configure the button bindings
        configureButtonBindings();

        SmartDashboard.putNumber("tx", tx.getDouble(0.0));
        SmartDashboard.putNumber("ty", ty.getDouble(0.0));
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));

        aimRobotAtTag.whileTrue(new PIDCommand(
            new PIDController(Constants.Swerve.driveKP, Constants.Swerve.driveKI, Constants.Swerve.driveKD), 
            () -> tx.getDouble(0.0), 
            0, 
            (output) -> s_Swerve.drive(new Translation2d(0, output), 0, false, false), 
            s_Swerve));

        /*
        positionRobotAtTag.onTrue(new PIDCommand(
            new PIDController(Constants.Swerve.driveKP, Constants.Swerve.driveKI, Constants.Swerve.driveKD), 
            () -> ty.getDouble(0.0), 
            1, 
            (output) -> s_Swerve.drive(new Translation2d(output, 0.0), 0, false, false), 
            s_Swerve));
            */
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new Autonomous1(s_Swerve);
    }
}
