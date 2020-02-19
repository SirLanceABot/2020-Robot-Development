package frc.components.drivetrain;

import frc.robot.Port;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import frc.sensors.NavX;
import frc.shuffleboard.AutonomousTab.StartingLocation;

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

    private static NavX navX = NavX.getInstance();

    private static boolean driveInitFlag = true;
    private static double targetDistance = 0.0;
    private static double ticksPerRotation = 4096; //TODO: need to find real value
    private static double diameterOfWheel = 6; // in inches;
    private static double distanceToTravel = 0.0;
    private static double startingEncoderValue = 0.0;
    private static double distanceTraveled = 0.0;
    private static boolean movingForward = false;
    private static double percentMax = 0.75;

    private static boolean rotationInitFlag = true;
    private static double targetRotation = 0.0;
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

    private double inchesToTicks(double inches)
    {
        return (inches/(diameterOfWheel * Math.PI) * ticksPerRotation);
    }
    /**
     * Drives a distance forward
     * IN INCHES
     * @param distance
     */
    public boolean drive(double distance)
    {
        if(driveInitFlag)
        {
            driveInitFlag = false;
            startingEncoderValue = (getLeftPosition() + getRightPosition()) / 2.0;
            distanceToTravel = inchesToTicks(distance) -  startingEncoderValue;
            targetDistance = inchesToTicks(distance) + startingEncoderValue;
            if(targetDistance < 0)
            {
                movingForward = false;
            }
            else
            {
                movingForward = true;
            }
        }

        distanceTraveled = ((getLeftPosition() + getRightPosition()) / 2.0) - startingEncoderValue;
        
        if(Math.abs(distanceTraveled) > targetDistance - 5 && Math.abs(distanceTraveled) < targetDistance + 5)
        {
            driveInitFlag = true;
            westCoastDrive(0.0, 0.0);
            return true;
        }
        else
        {   
            if(Math.abs(distanceTraveled/distanceToTravel) > 0.1)
            {
                if(movingForward)
                {
                    westCoastDrive(distanceTraveled/distanceToTravel * percentMax, 0);
                    
                }
                else
                {
                    westCoastDrive(-distanceTraveled/distanceToTravel * percentMax, 0);
    
                }
            }
            else
            {
                if(movingForward)
                {
                    westCoastDrive(0.1, 0);
                }
                else
                {
                    westCoastDrive(-0.1, 0);
                }
            }
            return false;
        }
    }

        


    /**
     * Relative rotation, NOT ABSOLUTE
     * @param rotation
     */
    public void rotate(double rotation)
    {
        
        double currentAngle = navX.getAngle();
        double targetAngle = rotation + currentAngle;
        if(targetAngle < -1.0)
        {   
            westCoastDrive(0.0, -0.25);
        }
        else if(targetAngle > 1.0)
        {
            westCoastDrive(0.0, 0.25);
        }
        else
        {
            westCoastDrive(0.0, 0.0);
        }
    }

    /**
     * Absolute Rotation, ABSOLUTE
     * @param rotation
     */
    public boolean rotateTo(double rotation)
    {
        if(rotationInitFlag)
        {
            targetRotation = rotation; 
            rotationInitFlag = false;
            return false;
        }
        double currentAngle = navX.getAngle();

        if(targetRotation - currentAngle  < -1.0)
        {   
            westCoastDrive(0.0, -0.25);
            return false;
        }
        else if(targetRotation - rotation > 1.0)
        {
            westCoastDrive(0.0, 0.25);
            return false;
        }
        else
        {
            westCoastDrive(0.0, 0.0);
            return true;
        }    
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