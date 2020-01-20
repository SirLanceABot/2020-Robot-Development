package frc.components.shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.sensors.NavX;


/**
 * Class for controlling the turret that aims the shooter on the X plane
 * 0 degrees is straight ahead, -180 to the left, +180 to the right
 * @author Maxwell Li
 */
public class Turret extends PIDSubsystem
{
    private static final double kPORPORTIONAL = 0.0;
    private static final double kINTEGRAL = 0.0;
    private static final double kDERIVATIVE = 0.0;
    private static final double kFEEDFORWARD = 0.0;
    private static final double kTOLERANCE = 0.0;
    private static final int kMOTOR_ID = 0;
    private static final double kZERO_LOCATION = 0; //TODO: Find out what value is straight ahead
    private static final int kTIMEOUT_MS = 30;
    private static final double kTOTAL_TICKS = 0.0; //TODO: Find out how many total ticks there are



    private static NavX navX = NavX.getInstance();
    private static TalonSRX motor = new TalonSRX(kMOTOR_ID);
    private static double currentPostion;
    private static boolean isMoving = false;
    private static Turret instance = new Turret();

    private Turret()
    {
        super("Turret", kPORPORTIONAL, kINTEGRAL, kDERIVATIVE, kFEEDFORWARD);
        System.out.println(this.getClass().getName() + ": Started Constructing");
        motor.configFactoryDefault();
        setAbsoluteTolerance(0.05);
        getPIDController();
        setInputRange(-180, 180);
        setAbsoluteTolerance(kTOLERANCE);
		/* Configure talon with feedback device to double check CANifier */
        motor.configSelectedFeedbackSensor(	FeedbackDevice.QuadEncoder, 0, kTIMEOUT_MS); //TODO: Find out port
        System.out.println(this.getClass().getName() + ": Finished Constructing");        
    }

    public static Turret getInstance()
    {
        return instance;
    }

      /**
     * init command needed for the PID control
     */
    public void initDefaultCommand()
    {

    }

    /**
     * @return returns the value that the PID controller should use
     */
    protected double returnPIDInput()
    {
        // return masterMotor.getMotorOutputVoltage();
        return getCurrentAngle(); // returns the sensor value that is providing the feedback for the system
    }

    /**
    * uses the PID to control the motor
    */
    protected void usePIDOutput(double output)
    {
        setSpeed(output);// this is where the computed output value fromthe PIDController is applied to the motor
        setIsMoving(true);
    }

    /**
     * Private function that handles all of the setting of motor speed
     * @param speed
     */
    private void setSpeed(double speed)
    {
        motor.set(ControlMode.PercentOutput, speed);
        setCurrentPosition(getEncoderPosition());
        setIsMoving(true);
    }

    /**
     * Stops the motors
     */
    private void stop()
    {
        motor.set(ControlMode.PercentOutput, 0.0);
        setCurrentPosition(getEncoderPosition());
        setIsMoving(false);
    }

    /**
     * sets the current position of the turret (ticks)
     * @param position
     */
    private void setCurrentPosition(double position)
    {
        currentPostion = position;
    }

    /**
     * Setter method for the encoder position (in encoder units)
     * @param position
     */
    private void setEncoderPosition(int position)
    {
		motor.setSelectedSensorPosition(position, 0, kTIMEOUT_MS);
    }

    /**
     * Getter method to get the position in encoder untis
     * @return
     */
    public double getEncoderPosition()
    {
        return motor.getSelectedSensorPosition(0);
    }

    /**
     * gets the current position (absolute degrees)
     * @return
     */
    public double getCurrentPosition()
    {
        return currentPostion;
    }

    /**
     * Uses the current position in encoder values to calc the angle (absolute)
     * @return the absolute angle of the turret
     */
    public double getCurrentAngle()
    {
        return ((currentPostion - kZERO_LOCATION) / kTOTAL_TICKS) * 180.0;
    }


    /**
     * sets the instance variable that determines if it is running
     * @param moving
     */
    private static void setIsMoving(boolean moving)
    {
        isMoving = moving;
    }

    /**
     * getter function for the isRunning boolean
     * @return true if it is running, false if it is not
     */
    public boolean getIsMoving()
    {
        return isMoving;
    }

    /**
     * Init function to be run to zero the location of the turret out.
     * Can be run at the init for auto and then again for teleop so the 
     * turret always starts in the same position 
     */
    public void init()
    {   
        double currentPositionInTicks = getEncoderPosition();

        if(currentPositionInTicks > kZERO_LOCATION + 5)
        {
            setSpeed(-0.1);
        }
        else if(currentPositionInTicks < kZERO_LOCATION - 5)
        {
            setSpeed(0.1);
        }
        else
        {
            setSpeed(0.0);
            setEncoderPosition(0); //TODO: Testing
        }
    }

    /**
     * Moves the shooter to an absolute position (in degrees)
     * uses a PID loop
     * @param angle
     */
    public void rotateTo(double angle)
    {
        usePIDOutput(angle);
        // motor.configMotionSCurveStrength(curveStrength, timeoutMs)
        // motor.set(ControlMode.MotionMagic, 2);
    }
    

    /**
     * Rotates the turret to a relative position (in degrees)
     * Uses a PID Loop
     * @param angle
     */
    public void rotate(double angle)
    {
        usePIDOutput(angle + getCurrentAngle());
    }

    public void rotateToWall()
    {
        double targetAngle = navX.getAngleOfPowerPortWall();
        double currentAngle = navX.getAngleOfPowerPortWall();

        usePIDOutput(targetAngle - currentAngle);
    }
}