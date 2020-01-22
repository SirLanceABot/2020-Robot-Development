package frc.components.climber;

// Import material to implement motors, encoders, and controls.
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;

public class Arm
{
    private static final int DEFAULT_POSITION = 10; // TODO: find actual default encoder position 
    private static final int ERROR_THRESHOLD = 5; // TODO: find actual threshold we want for the robot
    // TODO: What is the identity of the motor controller that extends the arm? Mr. Vanderweide says the motor controller is not a Talon but a SparkMAX brushless motor.
    
    private static CANSparkMax armMotor = new CANSparkMax(4, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANEncoder armEncoder = armMotor.getEncoder();

    private static Arm instance = new Arm();

    /**
     * The constructor for the Arm class. 
     */
    private Arm()
    {
        
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
     * Sets the position of the encoder.
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
     * The method to extend the Arm. 
     * Arm extends at half power, .5 meaning 50% power (0.5).
     */
    public void extendArm()
    {
        setExtensionSpeed(0.5);
    }

    /**
     * The method to retract the Arm.
     * Arm retracts at half power, -.5 meaning -50% power (-0.5).
     */
    public void retractArm()
    {
        setExtensionSpeed(-0.5);
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
            extendArm();
        }
        else if(currentPosition > DEFAULT_POSITION + ERROR_THRESHOLD)
        {
            retractArm();
        }
        else 
        {
            stopArm();
        }
    }
}