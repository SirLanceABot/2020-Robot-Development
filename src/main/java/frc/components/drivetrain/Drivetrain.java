package frc.components.drivetrain;

import frc.robot.Port;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class Drivetrain extends DifferentialDrive
{
    private static final String className = new String("[Drivetrain]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private static WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(Port.Motor.DRIVETRAIN_FRONT_RIGHT);
    private static WPI_TalonSRX backRightMotor = new WPI_TalonSRX(Port.Motor.DRIVETRAIN_BACK_RIGHT);
    private static WPI_TalonSRX backLeftMotor = new WPI_TalonSRX(Port.Motor.DRIVETRAIN_BACK_LEFT);
    private static WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(Port.Motor.DRIVETRAIN_FRONT_LEFT);

    private static SpeedControllerGroup rightMotors = new SpeedControllerGroup(frontRightMotor, backRightMotor);
    private static SpeedControllerGroup leftMotors = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);

    private static Drivetrain instance = new Drivetrain();

    private Drivetrain()
    {
        super(leftMotors, rightMotors);
        
        System.out.println(className + " : Constructor Started");


        frontRightMotor.configFactoryDefault();
        frontLeftMotor.configFactoryDefault();
        backRightMotor.configFactoryDefault();
        backLeftMotor.configFactoryDefault();

        //These invert the motor from the firmware on the motor controller
        frontRightMotor.setInverted(true);
        frontLeftMotor.setInverted(false);
        backRightMotor.setInverted(true);
        backLeftMotor.setInverted(false);

		frontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
		frontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
		backRightMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
		backLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
		
		resetEncoders();

        /**
         * This one inverts within software (flips a boolean)
         * // motorsRight.setInverted(false);
         */

         //By default the differential drive is inverted so this sets it so that it is not inverted
        setRightSideInverted(false);

        System.out.println(className + ": Constructor Finished");
    }

    public static Drivetrain getInstance()
    {
        return instance;
    }

    public void westCoastDrive(double move, double rotate)
    {
        super.arcadeDrive(move, rotate);
    }

    public void westCoastDrive(double move, double rotate, Boolean squared)
    {
        super.arcadeDrive(move, rotate, squared);
    }

    public void move(int distance)
    {

    }

    public void rotate(int rotation)
    {

    }

    public void resetEncoders()
    {
        frontRightMotor.setSelectedSensorPosition(0);
        frontLeftMotor.setSelectedSensorPosition(0);
        backRightMotor.setSelectedSensorPosition(0);
        backLeftMotor.setSelectedSensorPosition(0);
    }

    public void stop()
    {
        westCoastDrive(0, 0);
    }

    public double getLeftPosition()
    {
        return (frontLeftMotor.getSelectedSensorPosition(0)
        + backLeftMotor.getSelectedSensorPosition(0)) / 2.0;
    }

    public double getRightPosition()
    {
        return (frontRightMotor.getSelectedSensorPosition(0)
        + backRightMotor.getSelectedSensorPosition(0)) / 2.0;
    }

    public void setLeftPower(double power)
    {
        frontLeftMotor.set(power);
        backLeftMotor.set(power);
    }

    public void setRightPower(double power)
    {
        frontRightMotor.set(power);
        backRightMotor.set(power);
    }
}