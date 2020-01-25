package frc.components.climber;

// Import material to implement motors, encoders, and controls.
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;

/**
 * Class to control the Arm subsystem of the Climber.
 * @author Annika Price
 */
public class Arm
{
    // Declare and initialize private instance variables.
    private static CANSparkMax armMotor = new CANSparkMax(4, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANEncoder armEncoder = armMotor.getEncoder();
    private static final int DEFAULT_POSITION = 10; // TODO: find actual default encoder position 
    private static final int ERROR_THRESHOLD = 5; // TODO: find actual threshold we want for the robot
    private static final double MINIMUM_HEIGHT = 0.0;
    private static final double MAXIMUM_HEIGHT = 50.0;

    private static Arm instance = new Arm();

    /**
     * The constructor for the Arm class. 
     */
    private Arm()
    { 
        armMotor.restoreFactoryDefaults();
    }

    /**
     * The method to retrieve the instance of an Arm.
     * @return instance 
     */
    public static Arm getInstance()
    {
        return instance;
    }

    /**
     * The method to retrieve the current encoder position.
     * @return extensionMotor.getSelectedSensorPosition(0) 
     */
    public double getEncoderPosition()
    {
        return armEncoder.getPosition();
    }

    /**
     * The method to set the position of the encoder.
     * @param position The position of the encoder.
     */
    public void setEncoderPosition(int position)
    {
        armEncoder.setPosition(position);
    }

    /**
     * Sets the speed of the extensionMotor.
     * @param speed The speed at which the Arm extends. Values are from -1 to 1.
     */
    private void setExtensionSpeed(double speed)
    {
        armMotor.set(speed);
    }

    /**
     * The method to retract the Arm.
     * @param speed (this value is modified to always be negative)
     */
    public void retractArm(double speed)
    {
        double currentPosition = getEncoderPosition();
        if(currentPosition > MINIMUM_HEIGHT - ERROR_THRESHOLD)
        {
            setExtensionSpeed(-Math.abs(speed));
        }
        else
        {
            System.out.println("Cannot lower the arm. It is already at the minimum height.");
        }
    }

    /**
     * The method to extend the Arm. 
     * @param speed (this value is modified to always be positive)
     */
    public void extendArm(double speed)
    {
        double currentPosition = getEncoderPosition();
        if(currentPosition < MAXIMUM_HEIGHT + ERROR_THRESHOLD)
        {
            setExtensionSpeed(Math.abs(speed));
        }
        else
        {
            System.out.println("Cannot raise the arm. It is already at the maximum height.");
        }
    }

    /**
     * The method to stop the extension or retraction of the Arm.
     */
    public void stopArm()
    {
        setExtensionSpeed(0.0);
    }

    /**
     * The method to set the Arm to the default position.
     */
    public void setArmToDefaultPosition()
    {
        double currentPosition = getEncoderPosition();
        if(currentPosition < DEFAULT_POSITION - ERROR_THRESHOLD)   
        {
            extendArm(0.5);
        }
        else if(currentPosition > DEFAULT_POSITION + ERROR_THRESHOLD)
        {
            retractArm(-0.5);
        }
        else 
        {
            stopArm();
        }
    }
}