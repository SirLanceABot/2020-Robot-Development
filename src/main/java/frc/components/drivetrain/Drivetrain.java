package frc.components.drivetrain;

import frc.components.motor.MyVictorSPX;
import frc.robot.Port;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.sensors.NavX;


/**
 * Fast is 9.56 to 1
 * Slow is 18 to 1
 */
public class Drivetrain extends DifferentialDrive
{
    private static final String className = new String("[Drivetrain]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private static WPI_TalonFX frontRightMotor = new WPI_TalonFX(Port.Motor.CAN_DRIVETRAIN_FRONT_RIGHT);
    private static WPI_TalonFX backRightMotor = new WPI_TalonFX(Port.Motor.CAN_DRIVETRAIN_BACK_RIGHT);
    private static WPI_TalonFX backLeftMotor = new WPI_TalonFX(Port.Motor.CAN_DRIVETRAIN_BACK_LEFT);
    private static WPI_TalonFX frontLeftMotor = new WPI_TalonFX(Port.Motor.CAN_DRIVETRAIN_FRONT_LEFT);
    //private static DoubleSolenoid coolantSolenoid = new DoubleSolenoid(Port.Pneumatic.DRIVERTRAIN_COOLING_ON, Port.Pneumatic.DRIVERTRAIN_COOLING_OFF);


    //private static MyTalonFX fr = new MyTalonFX(Port.Motor.CAN_DRIVETRAIN_FRONT_RIGHT);

    private static SpeedControllerGroup rightMotors = new SpeedControllerGroup(frontRightMotor, backRightMotor);
    private static SpeedControllerGroup leftMotors = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);

    private static NavX navX = NavX.getInstance();

    private static boolean driveInitFlag = true;
    private static double targetDistance = 0.0;
    private static double ticksPerRotation = 2048; //TODO: need to find real value
    private static double diameterOfWheel = 6; // in inches;
    private static double distanceToTravel = 0.0;
    private static double startingEncoderValue = 0.0;
    private static double distanceTraveled = 0.0;
    private static boolean movingForward = false;
    private static double percentMax = 0.75;

    private static boolean rotationInitFlag = true;
    private static double targetRotation = 0.0;
    
    private static Shifter shifter = Shifter.getInstance();

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

        frontRightMotor.setNeutralMode(NeutralMode.Brake);
        frontLeftMotor.setNeutralMode(NeutralMode.Brake);
        backRightMotor.setNeutralMode(NeutralMode.Brake);
        backLeftMotor.setNeutralMode(NeutralMode.Brake);

        frontRightMotor.configReverseSoftLimitEnable(false);
        frontLeftMotor.configReverseSoftLimitEnable(false);
        backRightMotor.configReverseSoftLimitEnable(false);
        backLeftMotor.configReverseSoftLimitEnable(false);

        frontRightMotor.configForwardSoftLimitEnable(false);
        frontLeftMotor.configForwardSoftLimitEnable(false);
        backRightMotor.configForwardSoftLimitEnable(false);
        backLeftMotor.configForwardSoftLimitEnable(false);

        


		frontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
		frontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
		backRightMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
		backLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
		
        frontRightMotor.setSelectedSensorPosition(0);
        frontLeftMotor.setSelectedSensorPosition(0);
        backRightMotor.setSelectedSensorPosition(0);
        backLeftMotor.setSelectedSensorPosition(0);

       


        /**
         * This one inverts within software (flips a boolean)
         * // motorsRight.setInverted(false);
         */

         //By default the differential drive is inverted so this sets it so that it is not inverted
        setRightSideInverted(false);

        System.out.println(className + ": Constructor Finished");
    }

    private static void configTalon(WPI_TalonFX motor, boolean inverted, NeutralMode neutralMode, boolean forwardSoftLimit, boolean reverseSoftLimit,
                                    double peakStatorCurrent, double peakSupplyCurrent, double peakCurrentDuration, double continousCurrentLimit, double openLoopRamp)
    {
        motor.configFactoryDefault();
        motor.setInverted(inverted);
        motor.setNeutralMode(neutralMode);
        motor.configForwardSoftLimitEnable(forwardSoftLimit);
        motor.configReverseSoftLimitEnable(reverseSoftLimit);
        motor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 20, 25, 1.0));
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 10, 15, 0.5));
        motor.configOpenloopRamp(openLoopRamp);
    }

    public static Drivetrain getInstance()
    {
        return instance;
    }

    public void coolMotors()
    {
        //coolantSolenoid.set(DoubleSolenoid.Value.kReverse);   
    }

    public void stopCoolingMotors()
    {
        //coolantSolenoid.set(DoubleSolenoid.Value.kForward);   
    }

    public void westCoastDrive(double move, double rotate)
    {
        //System.out.println("Westcoast is driving at: " + move);
        super.arcadeDrive(move, rotate);
    }

    public void westCoastDrive(double move, double rotate, Boolean squared)
    {
        super.arcadeDrive(move, rotate, squared);
    }

    private double inchesToTicks(double inches)
    {
        //System.out.println((inches/(diameterOfWheel * Math.PI) * ticksPerRotation) * 9.56);
        return (inches/(diameterOfWheel * Math.PI) * ticksPerRotation) * 18.00;//9.56;
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
            System.out.println("Starting Encoder Value" + startingEncoderValue);
            System.out.println("Distance to Travel" + distanceToTravel);
            System.out.println("Target Distance" + targetDistance);


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
        System.out.println(distanceTraveled + "\t" + distanceToTravel);
        // System.out.println("Left Position: " + getLeftPosition() + "\t" +  "Right Position: " + getRightPosition());

        
        if(Math.abs(distanceTraveled) > Math.abs(targetDistance) - 100)
        {
            //System.out.println("In the if");
            driveInitFlag = true;
            westCoastDrive(0.0, 0.0);
            return true;
        }
        else
        {   
            //System.out.println("In the else");
            double speed = Math.abs((distanceToTravel - distanceTraveled) /distanceToTravel) * percentMax;
            System.out.println(speed);
            if(Math.abs(speed) > 0.5)
            {
                if(movingForward)
                {
                    westCoastDrive(((distanceToTravel - distanceTraveled) /distanceToTravel) * percentMax, 0);
                    //System.out.println(((distanceToTravel - distanceTraveled) /distanceToTravel) * percentMax);
                }
                else
                {
                    westCoastDrive(((-distanceToTravel - distanceTraveled) /distanceToTravel) * percentMax, 0);
                    //System.out.println(((distanceToTravel - distanceTraveled)/distanceToTravel) * percentMax);
                }
            }
            else
            {
                if(movingForward)
                {
                    westCoastDrive(0.5, 0);
                }
                else
                {
                    westCoastDrive(-0.5, 0);
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
        return (frontLeftMotor.getSelectedSensorPosition()
        + backLeftMotor.getSelectedSensorPosition()) / 2.0;
    }

    public double getRightPosition()
    {
        return (frontRightMotor.getSelectedSensorPosition()
        + backRightMotor.getSelectedSensorPosition()) / 2.0;
    }

    public double getFrontLeftPosition()
    {
        return frontLeftMotor.getSelectedSensorPosition(0);
    }

    public double getFrontRightPosition()
    {
        return frontRightMotor.getSelectedSensorPosition(0);
    }

    public double getBackLeftPosition()
    {
        return backLeftMotor.getSelectedSensorPosition(0);
    }

    public double getBackRightPosition()
    {
        return backRightMotor.getSelectedSensorPosition(0);
    }

    public void setLeftPower(double power)
    {
        frontLeftMotor.set(power);
        backLeftMotor.set(power);
    }

    /**
     * FOR USE ONLY IN TEST!
     * @param speed
     */
    @Deprecated
    public void setLeftSpeedTestOnly(double speed)
    {
        setLeftPower(speed);
    }

    public void setRightPower(double power)
    {
        frontRightMotor.set(power);
        backRightMotor.set(power);
    }

    /**
     * FOR USE ONLY IN TEST!
     * @param speed
     */
    @Deprecated
    public void setRightSpeedTestOnly(double speed)
    {
        setRightPower(speed);
    }

    public void forceShiftUp()
    {
        shifter.forceShiftUp();
    }

    public void forceShiftDown()
    {
        shifter.forceShiftDown();
    }
}