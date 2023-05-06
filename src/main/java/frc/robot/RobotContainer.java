package frc.robot;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.autos.Center930;
import frc.robot.autos.ChargeStationAutoMid;
import frc.robot.autos.ChargeStationAutonomous;
import frc.robot.autos.DropCube;
import frc.robot.autos.DropMoveLeftGoBack;
import frc.robot.autos.DropMoveRightGoBack;
import frc.robot.autos.DropOnlyCargoAuto;
import frc.robot.autos.LeftChargeStationAuto;
import frc.robot.autos.MidConeDeliverSide;
import frc.robot.autos.NoArmAutoSide1;
import frc.robot.commands.AimSwerveAtTagCommand;
import frc.robot.commands.BalanceCommand;
import frc.robot.commands.ExtendArm;
import frc.robot.commands.MoveArm;
import frc.robot.commands.MoveRobotCommand;
import frc.robot.commands.MoveShoulder;
import frc.robot.commands.MoveShoulderDeliverHigh;
import frc.robot.commands.MoveShoulderHigh;
import frc.robot.commands.MoveShoulderLow;
import frc.robot.commands.MoveShoulderMid;
import frc.robot.commands.MoveShoulderStart;
import frc.robot.commands.OuttakeCommand;
import frc.robot.commands.RetractArm;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Hand;
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
    public static Spark light = new Spark(8);
    private boolean isPurple = true;
    private boolean isSolid = true;

    //private static I2C m_lidar = new I2C(I2C.Port.kOnboard, 0x30);
    //private static CANdle candle = new CANdle(40, "Carnie");
    //private static CANdleConfiguration candleConfig = new CANdleConfiguration();

    /* Controllers */
    private final Joystick driver = new Joystick(Constants.DriverJoystickPort);
    private final Joystick armController = new Joystick(Constants.ArmDriverJoystickPort);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;
    public static double driverDivisor = Constants.fastSpeed;

    /* Drive Assistant Controls */
    private final int shoulderAxis = XboxController.Axis.kLeftY.value;
    private final int armAxis = XboxController.Axis.kRightY.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
  //private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton aimRobotAtTag = new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton moveRobotLeft = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton moveRobotRight = new JoystickButton(driver, XboxController.Button.kB.value);
    private final JoystickButton fullPowerOuttake = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton togglePattern = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
    private final JoystickButton slowFast = new JoystickButton(driver, XboxController.Button.kStart.value);
    //private final JoystickButton resetPositionButton = new JoystickButton(driver, XboxController.Button.kBack.value);


    /* Shooter/ Arm Controller buttons */ 
    private final JoystickButton Intake = new JoystickButton(armController, XboxController.Button.kA.value);
    private final JoystickButton Outake = new JoystickButton(armController, XboxController.Button.kY.value);
    private final JoystickButton DeliverMid = new JoystickButton(armController, XboxController.Button.kX.value);
   // private final JoystickButton DeliverLow = new JoystickButton(armController, XboxController.Button.kB.value);
    private final JoystickButton PickupDropStation = new JoystickButton(armController, XboxController.Button.kLeftBumper.value);
    private final JoystickButton PickupLoadStation = new JoystickButton(armController, XboxController.Button.kRightBumper.value);
    private final JoystickButton ChangeWrist = new JoystickButton(armController, XboxController.Button.kB.value);
    //private final JoystickButton zeroArmAndShoulder = new JoystickButton(armController, XboxController.Button.kBack.value);
    private final JoystickButton deliverHigh = new JoystickButton(armController, XboxController.Button.kBack.value);
    private final JoystickButton startPosition = new JoystickButton(armController, XboxController.Button.kStart.value);
    //private final JoystickButton readLidar = new JoystickButton(armController, XboxController.Button.kStart.value);

    //play music
    //private final JoystickButton playMusicButton = new JoystickButton(armController, XboxController.Button.kA.value);

    /* Subsystems */
    public static final Swerve s_Swerve = new Swerve();
    public static final Shoulder m_shoulder = new Shoulder();
    public static final Arm m_Arm = new Arm();
    public static final Hand m_Hand = new Hand();

    /* Limelight */
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private static NetworkTableEntry ty = table.getEntry("ty");
    private static NetworkTableEntry tx = table.getEntry("tx");

    /* Autonomous Commands */
    private Command m_DropCube = new SequentialCommandGroup(
        new DropCube(s_Swerve, m_Hand, m_Arm, m_shoulder),
        new BalanceCommand(s_Swerve)
    );
         
    private Command m_ChargeStationAutonomous = new SequentialCommandGroup(
        new ChargeStationAutonomous(s_Swerve, m_Hand, m_Arm, m_shoulder, light),
        new BalanceCommand(s_Swerve)
    );

    private Command m_ChargeStationAutonomousMid = new SequentialCommandGroup(
        new ChargeStationAutoMid(s_Swerve, m_Hand, m_Arm, m_shoulder, light),
        new BalanceCommand(s_Swerve)
    );

    private Command m_Center930 = new SequentialCommandGroup(
        new Center930(s_Swerve, m_Hand, m_Arm, m_shoulder, light),
        new BalanceCommand(s_Swerve)
    );
    /*
    private Command m_LeftChargeStationAuto = new SequentialCommandGroup(
        new LeftChargeStationAuto(s_Swerve, m_Hand, m_Arm, m_shoulder),
        new BalanceCommand(s_Swerve)
    );
    */
    private Command m_LeftChargeStationAuto = new LeftChargeStationAuto(s_Swerve, m_Hand, m_Arm, m_shoulder, light);

    private Command m_MidConeDeliverSide = new MidConeDeliverSide(s_Swerve, m_Hand, m_Arm, m_shoulder, light);

    private Command m_NoArmAuto = new SequentialCommandGroup(
        //new NoArmAutoSide(s_Swerve, m_elbow, m_shoulder)
        new NoArmAutoSide1(s_Swerve, m_Arm, m_shoulder)
        //new BalanceCommand(s_Swerve)
    );


    private Command m_DropOnlyCargoAuto = new SequentialCommandGroup(
        new DropOnlyCargoAuto(s_Swerve, m_Arm, m_shoulder)

    );
    private Command m_DropMoveLeftGoBack = new SequentialCommandGroup(
        new DropMoveLeftGoBack(s_Swerve, m_Hand, m_Arm, m_shoulder)

    );
    private Command m_DropMoveRightGoBack = new SequentialCommandGroup(
        new DropMoveRightGoBack(s_Swerve, m_Hand, m_Arm, m_shoulder)
    );


    
    private static SendableChooser<Command> m_chooser = new SendableChooser<>();

    


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

        //Turn the limelight LED off.
        //DEFAULT(0), OFF(1), BLINK(2), ON(3);
        table.getEntry("ledMode").setNumber(1);

        m_Arm.zeroEncoderPosition();
        m_shoulder.zeroEncoderPosition();
        CameraServer.startAutomaticCapture();
        //lights
        //candleConfig.stripType = LEDStripType.RGB;
        //candleConfig.brightnessScalar = 1;
        //candle.configAllSettings(candleConfig);
        //candle.setLEDs(75 ,0, 130);

       // Limelight.getInstance();
       //https://docs.limelightvision.io/en/latest/getting_started.html#basic-programming
       //   Number 1 turns off the light; 2 blinks; 3 turns on; 

       table.getInstance().getEntry("ledMode").setNumber(1);
        s_Swerve.setDefaultCommand(
            new TeleopSwerve( 
                s_Swerve, 
                () -> -getTranslationSpeed(), 
                () -> -getStrafeSpeed(), 
                () -> -driver.getRawAxis(rotationAxis)/1.5, 
                () -> false
            )
        );
        

        m_shoulder.setDefaultCommand(
            new MoveShoulder(m_shoulder, armController, shoulderAxis)
        );

        m_Arm.setDefaultCommand(
            new MoveArm(m_Arm, armController, armAxis)
        );

        // Configure the button bindings
        configureButtonBindings();

        // A chooser for autonomous commands
        m_chooser.setDefaultOption("Charge Station High Balance Auto", m_ChargeStationAutonomous);
        m_chooser.addOption("Charge Station Mid Balance Auto", m_ChargeStationAutonomousMid);
        m_chooser.addOption("Drop cone High, Goes Back", m_LeftChargeStationAuto);
        m_chooser.addOption("Drop cone Mid, Goes Back", m_MidConeDeliverSide);
        m_chooser.addOption("Just Drop Cube, Goes Back (no arm)", m_DropCube);
        m_chooser.addOption("930 auto command red", m_Center930);
        /*
        m_chooser.addOption("Far Side Auto, Does Not Rotate Now, Moves back", m_SideAutonomous);
       // m_chooser.addOption("LeftChargeStationAuto, Rotates then moves back", m_LeftChargeStationAuto);
        m_chooser.addOption("No Arm Auto Side, Only moves back", m_NoArmAuto);
        m_chooser.addOption("Drop Only Cargo. Does not Move.", m_DropOnlyCargoAuto);
        m_chooser.addOption("Drop, Moves to your Left, Goes Back", m_DropMoveRightGoBack);
        
*/

        // Put the chooser on the dashboard
        SmartDashboard.putData("Autonomous choices", m_chooser);
    }

    public void zeroArmAndShoulder(){
        m_Arm.zeroEncoderPosition();
        m_shoulder.zeroEncoderPosition();
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
        moveRobotRight.onTrue(new MoveRobotCommand(s_Swerve, -2.5));

        /* X Button */
        moveRobotLeft.onTrue(new MoveRobotCommand(s_Swerve, 2.5));

        /* Right bumper Button */

        //Driver
        fullPowerOuttake.whileTrue(new InstantCommand(() -> m_Hand.HandMotor.set(-1)));
        fullPowerOuttake.onFalse(new InstantCommand(() -> m_Hand.HandMotor.set(0)));
        
        togglePattern.onTrue(new InstantCommand(() -> {
            isPurple = !isPurple;
            if (isPurple){
                light.set(0.91);
            }
            else {
                light.set(0.67);
            }          
        }));
        

        /* Shooter */
        //toggleWrist.onTrue(new InstantCommand(() -> Elbow.toggleWrist()));

        // Left Bumper Button 
        PickupDropStation.onTrue(new ParallelCommandGroup(
            new MoveShoulderLow (m_shoulder, 0.8),
            new RetractArm(m_Arm, 0.7)
        ));

        // Right Bumper Button
        PickupLoadStation.onTrue(new ParallelCommandGroup(
            new MoveShoulderHigh (m_shoulder, 0.8),
            new ExtendArm(m_Arm, 0.7)
        ));

        // A Button
        Intake.whileTrue(new OuttakeCommand(m_Hand, 0.4));
        Intake.onFalse(new InstantCommand(() -> m_Hand.HandMotor.neutralOutput()));
        
        // Y Button
        Outake.whileTrue(new InstantCommand(() -> m_Hand.HandMotor.set(-0.4)));
        Outake.onFalse(new InstantCommand(() -> m_Hand.HandMotor.set(0)));

        // X Button
        DeliverMid.onTrue(new ParallelCommandGroup(
            new SequentialCommandGroup(
                new WaitCommand(2),
                new MoveShoulderMid(m_shoulder, 0.8)                
            ),
            new ExtendArm(m_Arm, 0.7)
        ));

        // B Button
        ChangeWrist.onTrue(new InstantCommand(() -> Hand.Wrist.toggle()));

        //Start
        //readLidar.whileTrue(new InstantCommand(() -> SmartDashboard.putNumber("Lidar", m_lidar.getValue())));
      
        
        /* Start Button 
        DeliverLow.onTrue(new ParallelCommandGroup(
            new MoveShoulderLow (m_shoulder, 0.8),
            //new InstantCommand(() -> Hand.Wrist.set(false)),
            new ExtendArm(m_Arm, 0.6)
        ));*/

        // Back Button
        deliverHigh.onTrue(new ParallelCommandGroup(
            new MoveShoulderDeliverHigh(m_shoulder, 0.8),
            new ExtendArm(m_Arm, 0.7)
        ));
        /*
        zeroArmAndShoulder.onTrue(new ParallelCommandGroup(
            new InstantCommand(()-> m_shoulder.zeroEncoderPosition()),
            new InstantCommand(()-> m_Arm.zeroEncoderPosition())
        )); */

        //Driver
        startPosition.onTrue(new ParallelCommandGroup(
            new MoveShoulderStart (m_shoulder, 0.8),
            new RetractArm(m_Arm, 0.7)
        ));
        //Shooter
        //grab.onTrue(new InstantCommand(() -> Elbow.toggleGrabber()));


        slowFast.onTrue(new InstantCommand(() -> {
            if(driverDivisor == Constants.fastSpeed ){
                driverDivisor = Constants.slowSpeed;
            } else {
                driverDivisor = Constants.fastSpeed;
            }
            s_Swerve.setDefaultCommand(
                new TeleopSwerve( 
                    s_Swerve, 
                    () -> -driver.getRawAxis(translationAxis)*Math.abs(driver.getRawAxis(translationAxis))/driverDivisor, 
                    () -> -driver.getRawAxis(strafeAxis)*Math.abs(driver.getRawAxis(strafeAxis))/driverDivisor, 
                    () -> -driver.getRawAxis(rotationAxis)/1.5, 
                    () -> false
                )
            );
        }));


        
    }

    private double getTranslationSpeed(){
        double speed = driver.getRawAxis(translationAxis)*Math.abs(driver.getRawAxis(translationAxis))/driverDivisor;
        if (m_Arm.getEncoderPosition() < Constants.armExtendedPosition/2){
            return Math.min(speed, 0.75);
        } else {
            return speed;
        }
    }

    private double getStrafeSpeed(){
        double speed = driver.getRawAxis(strafeAxis)*Math.abs(driver.getRawAxis(strafeAxis))/driverDivisor;
        if (m_Arm.getEncoderPosition() < Constants.armExtendedPosition/2){
            return Math.min(speed, 0.75);
        } else {
            return speed;
        }
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
