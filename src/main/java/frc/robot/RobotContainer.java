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
import frc.robot.autos.ChargeStationAutonomous;
import frc.robot.autos.SideAutonomous;
import frc.robot.commands.AimSwerveAtTagCommand;
import frc.robot.commands.BalanceCommand;
import frc.robot.commands.MoveElbowToFloor;
import frc.robot.commands.MoveElbowToHigh;
import frc.robot.commands.MoveElbowToHome;
import frc.robot.commands.MoveElbowToLow;
import frc.robot.commands.MoveRobotCommand;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.Elbow;
import frc.robot.subsystems.Elbow.Position;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Swerve;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    //lights
    //private static CANdle candle = new CANdle(40, "Carnie");
    //private static CANdleConfiguration candleConfig = new CANdleConfiguration();
    private static boolean isPurple = true;

    /* Controllers */
    private final Joystick driver = new Joystick(Constants.DriverJoystickPort);
    private final Joystick armController = new Joystick(Constants.ArmDriverJoystickPort);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;
    public static double driverDivisor = 1.2;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
  //private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton aimRobotAtTag = new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton moveRobotRight = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton moveRobotLeft = new JoystickButton(driver, XboxController.Button.kB.value);
    private final JoystickButton moveRobotLeftOuter = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
    private final JoystickButton moveRobotRightOuter = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
 // private final JoystickButton slowFast = new JoystickButton(driver, XboxController.Button.kStart.value);
    private final JoystickButton balanceRobot = new JoystickButton(driver, XboxController.Button.kStart.value);
    private final JoystickButton changeColor = new JoystickButton(driver, XboxController.Button.kBack.value);

    /* Shooter/ Arm Controller buttons */ 
    private final JoystickButton grab = new JoystickButton(armController, XboxController.Button.kA.value);
    private final JoystickButton armLow = new JoystickButton(armController, XboxController.Button.kY.value);
    private final JoystickButton armFloor = new JoystickButton(armController, XboxController.Button.kX.value);
    private final JoystickButton armHigh = new JoystickButton(armController, XboxController.Button.kB.value);
    private final JoystickButton armHome = new JoystickButton(armController, XboxController.Button.kLeftBumper.value);
    //private final JoystickButton toggleGrabber = new JoystickButton(armController, XboxController.Button.kRightBumper.value);
    private final JoystickButton toggleWrist = new JoystickButton(armController, XboxController.Button.kRightBumper.value);



    //play music
    //private final JoystickButton playMusicButton = new JoystickButton(armController, XboxController.Button.kA.value);

    /* Subsystems */
    public static final Swerve s_Swerve = new Swerve();
    public static final Elbow m_elbow = new Elbow();
    public static final Shoulder m_shoulder = new Shoulder();

    /* Limelight */
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private static NetworkTableEntry ty = table.getEntry("ty");
    private static NetworkTableEntry tx = table.getEntry("tx");

    /* Autonomous Commands */
    private Command m_SideAutonomous = new SideAutonomous(s_Swerve);
    private Command m_ChargeStationAutonomous = new SequentialCommandGroup(
        new ChargeStationAutonomous(s_Swerve),
        new BalanceCommand(s_Swerve)
    );
    private static SendableChooser<Command> m_chooser = new SendableChooser<>();

    


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

        //lights
        //candleConfig.stripType = LEDStripType.RGB;
        //candleConfig.brightnessScalar = 1;
        //candle.configAllSettings(candleConfig);
        //candle.setLEDs(75 ,0, 130);

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
        m_chooser.setDefaultOption("Side Auto", m_SideAutonomous);
        m_chooser.addOption("ChargeStationAuto", m_ChargeStationAutonomous);
        
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
        /* Y Button */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));

        /* A Button */
        aimRobotAtTag.onTrue(new AimSwerveAtTagCommand(tx, 0.0, s_Swerve));

        /* B Button */
        moveRobotRight.onTrue(new MoveRobotCommand(s_Swerve, 2));

        /* X Button */
        moveRobotLeft.onTrue(new MoveRobotCommand(s_Swerve, -2));

        /* Right bumper Button */

        //Driver
        moveRobotRightOuter.onTrue(new MoveRobotCommand(s_Swerve, 4));

        //Shooter
        toggleWrist.onTrue(new InstantCommand(() -> Elbow.toggleWrist()));

        /* Left Bumper Button */
        moveRobotLeftOuter.onTrue(new MoveRobotCommand(s_Swerve, -4));
        
        /* Start Button */

        //Driver
        balanceRobot.whileTrue(new BalanceCommand(s_Swerve));

        //Shooter
        grab.onTrue(new InstantCommand(() -> Elbow.toggleGrabber()));


      /*   slowFast.onTrue(new InstantCommand(() -> {
            if(driverDivisor == 2 ){
                driverDivisor = 3;
            } else {
                driverDivisor = 2;
            }
            s_Swerve.setDefaultCommand(
                new TeleopSwerve(
                    s_Swerve, 
                    () -> -driver.getRawAxis(translationAxis)/driverDivisor, 
                    () -> -driver.getRawAxis(strafeAxis)/driverDivisor, 
                    () -> -driver.getRawAxis(rotationAxis)/driverDivisor, 
                    () -> true
                )
            );
        }));*/

        //back button
        changeColor.onTrue(new InstantCommand(() -> {

            /* 
            if (isPurple){
                //candle.setLEDs(255, 215, 0);
                isPurple = false;
            }
            else {
                //candle.setLEDs(75 ,0, 130);
                isPurple = true;
            }
            */
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
                    //new MoveShoulderToHome(m_shoulder),
                    new MoveElbowToHome(m_elbow)
                ),
                //Then, we want to set the position of the robot
                new InstantCommand(() -> m_elbow.setPosition(Position.Home))
            )    
        );

        armFloor.onTrue(
            new SequentialCommandGroup(
                new ParallelCommandGroup(
                    //new MoveShoulderToHome(m_shoulder),
                    new MoveElbowToFloor(m_elbow)
                ),
                new InstantCommand(() -> m_elbow.setPosition(Position.Floor))
            )
        );

        armLow.onTrue(
            new SequentialCommandGroup(
                new ParallelCommandGroup(
                    // Need Encoder
                    //new MoveShoulderToHigh(m_shoulder),
                    new MoveElbowToLow(m_elbow)
                ),
                new InstantCommand(() -> m_elbow.setPosition(Position.Low))
            )
        );
        
        armHigh.onTrue(
            new SequentialCommandGroup(
                new ParallelCommandGroup(
                    //new MoveShoulderToHigh(m_shoulder),
                    new MoveElbowToHigh(m_elbow)
                ),
                new InstantCommand(() -> m_elbow.setPosition(Position.High))
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
        /*
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod0.angleMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod0.driveMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod1.angleMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod1.driveMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod2.angleMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod2.driveMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod3.angleMotorID,"Carnie"));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod3.driveMotorID,"Carnie"));
        */
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod0.angleMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod0.driveMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod1.angleMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod1.driveMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod2.angleMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod2.driveMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod3.angleMotorID));
        orchestra.addInstrument(new TalonFX(Constants.Swerve.Mod3.driveMotorID));
        
        orchestra.play();
    }

    
    
}
