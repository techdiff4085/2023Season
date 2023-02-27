package frc.robot;

//import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.autos.Blue1Auto;
import frc.robot.autos.PathPlannerAuto2;
import frc.robot.autos.PathPlannerAuto4;
import frc.robot.commands.AimSwerveAtTagCommand;
import frc.robot.commands.BalanceCommand;
import frc.robot.commands.MoveElbowToFloor;
import frc.robot.commands.MoveElbowToHigh;
import frc.robot.commands.MoveElbowToHome;
import frc.robot.commands.MoveElbowToLow;
import frc.robot.commands.MoveRobotCommand;
import frc.robot.commands.MoveShoulderToHigh;
import frc.robot.commands.MoveShoulderToHome;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.Position;
import frc.robot.subsystems.Swerve;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);
    private final Joystick armController = new Joystick(1);


    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;
    public static int driverDivisor = 2;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
  //private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton aimRobotAtTag = new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton moveRobotRight = new JoystickButton(driver, XboxController.Button.kB.value);
    private final JoystickButton moveRobotLeft = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton moveRobotLeftOuter = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton moveRobotRightOuter = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
    private final JoystickButton slowFast = new JoystickButton(driver, XboxController.Button.kStart.value);

    /* Shooter/ Arm Controller buttons */ 
    private final JoystickButton armHome = new JoystickButton(armController, XboxController.Button.kA.value);
    private final JoystickButton armLow = new JoystickButton(armController, XboxController.Button.kY.value);
    private final JoystickButton armFloor = new JoystickButton(armController, XboxController.Button.kX.value);
    private final JoystickButton armHigh = new JoystickButton(armController, XboxController.Button.kB.value);
    private final JoystickButton toggleGrabber = new JoystickButton(armController, XboxController.Button.kRightBumper.value);
    private final JoystickButton toggleWrist = new JoystickButton(armController, XboxController.Button.kLeftBumper.value);



    //play music
    private final JoystickButton playMusicButton = new JoystickButton(armController, XboxController.Button.kA.value);

    /* Subsystems */
    public static final Swerve s_Swerve = new Swerve();
    public static final Arm m_arm = new Arm();

    /* Limelight */
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private static NetworkTableEntry ty = table.getEntry("ty");
    private static NetworkTableEntry tx = table.getEntry("tx");

    /* Autonomous commands */
    //private Command m_auto1 = new Autonomous1(s_Swerve);
    //private Command m_autoExample = new exampleAuto(s_Swerve);
    private Command m_PathPlannerAuto2 = new SequentialCommandGroup(
        new PathPlannerAuto2(s_Swerve),
        new BalanceCommand(s_Swerve)
    );
    private Command m_PathPlannerBlueAuto1 = new Blue1Auto(s_Swerve);
    private Command m_PathPlannerAuto4 = new PathPlannerAuto4(s_Swerve);
    private static SendableChooser<Command> m_chooser = new SendableChooser<>();

    


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

       // Limelight.getInstance();
       
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis)/driverDivisor, 
                () -> -driver.getRawAxis(strafeAxis)/driverDivisor, 
                () -> -driver.getRawAxis(rotationAxis)/driverDivisor, 
                () -> true
            )
        );

        // Configure the button bindings
        configureButtonBindings();

        SmartDashboard.putNumber("tx", tx.getDouble(0.0));
        SmartDashboard.putNumber("ty", ty.getDouble(0.0));

        // A chooser for autonomous commands
        
        m_chooser.addOption("PathPlannerAuto2", m_PathPlannerAuto2);
        m_chooser.setDefaultOption("Blue Auto 1", m_PathPlannerBlueAuto1);
        
        //m_chooser.setDefaultOption("Spinny Spin", m_PathPlannerAuto3);
        
        // Put the chooser on the dashboard
        SmartDashboard.putData("Autonomous choices", m_chooser);

    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        //Y Button
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));

        //A Button
        aimRobotAtTag.onTrue(new AimSwerveAtTagCommand(tx, 0.0, s_Swerve));

        //B Button
        moveRobotRight.onTrue(new MoveRobotCommand(s_Swerve, 2));

        //X Button
        moveRobotLeft.onTrue(new MoveRobotCommand(s_Swerve, -2));

        //Right bumper Button
        moveRobotRightOuter.onTrue(new MoveRobotCommand(s_Swerve, 4));

        //left bumper Button
        moveRobotLeftOuter.onTrue(new MoveRobotCommand(s_Swerve, -4));
        
        //start button
        slowFast.onTrue(new InstantCommand(() -> {
            if(driverDivisor == 2 ){
                driverDivisor = 3;
            } else {
                driverDivisor = 2;
            }
        }));

        /* can be removed ///JoystickButton ledOn = new JoystickButton(driver, XboxController.Button.kBack.value);
    
        Command ClearSticky = new ParallelCommandGroup(
          new InstantCommand(()-> new TalonFX(Constants.Swerve.Mod0.angleMotorID).clearStickyFaults()), 
          new InstantCommand(()-> new TalonFX(Constants.Swerve.Mod0.driveMotorID).clearStickyFaults()), 
          new InstantCommand(()-> new TalonFX(Constants.Swerve.Mod1.angleMotorID).clearStickyFaults()), 
          new InstantCommand(()-> new TalonFX(Constants.Swerve.Mod1.driveMotorID).clearStickyFaults()),
          new InstantCommand(()-> new TalonFX(Constants.Swerve.Mod2.angleMotorID).clearStickyFaults()), 
          new InstantCommand(()-> new TalonFX(Constants.Swerve.Mod2.driveMotorID).clearStickyFaults()),
          new InstantCommand(()-> new TalonFX(Constants.Swerve.Mod3.angleMotorID).clearStickyFaults()), 
          new InstantCommand(()-> new TalonFX(Constants.Swerve.Mod3.driveMotorID).clearStickyFaults())
          );

        ledOn.whileTrue(ClearSticky);
        */

        /* Arm Operator Buttons */
        armHome.onTrue(
            //We want to do the following commands sequentially
            new SequentialCommandGroup(
                //First, we want to move all the different arm components
                new ParallelCommandGroup(
                    new MoveShoulderToHome(m_arm),
                    new MoveElbowToHome(m_arm)
                ),
                //Then, we want to set the position of the robot
                new InstantCommand(() -> m_arm.setPosition(Position.Home))
            )    
        );

        //TODO do the rest of the arm buttons.
        armFloor.onTrue(
            new SequentialCommandGroup(
                new ParallelCommandGroup(
                    new MoveShoulderToHome(m_arm),
                    new MoveElbowToFloor(m_arm)
                ),
                new InstantCommand(() -> m_arm.setPosition(Position.Floor))
            )
        );

        armLow.onTrue(
            new SequentialCommandGroup(
                new ParallelCommandGroup(
                    // Need Encoder
                    new MoveShoulderToHigh(m_arm),
                    new MoveElbowToLow(m_arm)
                ),
                new InstantCommand(() -> m_arm.setPosition(Position.Low))
            )
        );
        
        armHigh.onTrue(
            new SequentialCommandGroup(
                new ParallelCommandGroup(
                    new MoveShoulderToHigh(m_arm),
                    new MoveElbowToHigh(m_arm)
                ),
                new InstantCommand(() -> m_arm.setPosition(Position.High))
            )
        );
        
        JoystickButton resetPositionButton = new JoystickButton(driver, XboxController.Button.kBack.value);
        resetPositionButton.onTrue(new InstantCommand(()-> s_Swerve.resetMotorPosition()));

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        SmartDashboard.putString("SelectedAutonomous", m_chooser.getSelected().getName());
        return m_chooser.getSelected();

    }
    public void PlayMusic(String Filename){
        Orchestra orchestra = new Orchestra();
        orchestra.loadMusic(Filename);
        
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod0.angleMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod0.driveMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod1.angleMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod1.driveMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod2.angleMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod2.driveMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod3.angleMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod3.driveMotorID,"Carnie"));
        /* 
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod0.angleMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod0.driveMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod1.angleMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod1.driveMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod2.angleMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod2.driveMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod3.angleMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod3.driveMotorID));
        */
        orchestra.play();
    }

    
    
}
