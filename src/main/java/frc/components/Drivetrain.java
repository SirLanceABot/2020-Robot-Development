package frc.components;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANEncoder;

public class Drivetrain extends DifferentialDrive
{
    // Example change
    //          ***TODO***  BEFORE RUNNING MUST DOUBLE CHECK THE ID VALUES OF THE CAN SPARK MAX          ***ERROR***              //
    // delete these 2 lines when the correct id's have been implemented
    private static CANSparkMax frontRightMotor = new CANSparkMax(1, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANSparkMax backRightMotor = new CANSparkMax(2, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANSparkMax backLeftMotor = new CANSparkMax(3, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANSparkMax frontLeftMotor = new CANSparkMax(4, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
  
    private static CANEncoder frontLeftEncoder = frontLeftMotor.getEncoder();
    private static CANEncoder frontRightEncoder = frontRightMotor.getEncoder();
    private static CANEncoder backLeftEncoder = backLeftMotor.getEncoder();
    private static CANEncoder backRightEncoder = backRightMotor.getEncoder();

    private static SpeedControllerGroup rightMotors = new SpeedControllerGroup(frontRightMotor, backRightMotor);
    private static SpeedControllerGroup leftMotors = new SpeedControllerGroup(backLeftMotor, frontLeftMotor);

    private static Drivetrain instance = new Drivetrain();

    private Drivetrain()
    {
        super(leftMotors, rightMotors);
        frontRightMotor.restoreFactoryDefaults();
        frontLeftMotor.restoreFactoryDefaults();
        backRightMotor.restoreFactoryDefaults();
        backLeftMotor.restoreFactoryDefaults();

        //These invert the motor from the firmware on the motor controller
        frontRightMotor.setInverted(true);
        frontLeftMotor.setInverted(false);
        backRightMotor.setInverted(true);
        backLeftMotor.setInverted(false);

        frontLeftEncoder.setPosition(0);
        frontRightEncoder.setPosition(0);
        backLeftEncoder.setPosition(0);
        backRightEncoder.setPosition(0);

        /**
         * This one inverts within software (flips a boolean)
         * // motorsRight.setInverted(false);
         */

         //By default the differential drive is inverted so this sets it so that it is not inverted
        setRightSideInverted(false);
    }

    public static Drivetrain getInstance()
    {
        return instance;
    }

    
    public void westcoastDrive(double move, double rotate)
    {
        super.arcadeDrive(move, rotate);
    }

    public void westcoastDrive(double move, double rotate, Boolean squared)
    {
        super.arcadeDrive(move, rotate, squared);
    }

}
