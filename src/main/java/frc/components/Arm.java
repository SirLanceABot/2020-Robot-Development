package frc.components;

// Import material to implement Talons and controls.
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Arm
{
    // What is the identity of the motor controller that extends the arm?
    // Mr. Vanderweide says the motor controller is not a Talon but a SparkMAX brushless motor.
    private static TalonSRX extensionMotor = new TalonSRX(0);
    private static Arm instance = new Arm();

    /**
     * The constructor for the Arm class. 
     */
    private Arm()
    {
        extensionMotor.configFactoryDefault();
    }

    /**
     * The method to retrieve the instance of an Arm.
     * @return instance 
     */
    public Arm getInstance()
    {
        return instance;
    }

    /**
     * Sets the speed of the extensionMotor.
     * @param speed The speed at which the Arm extends. Values are from -1 to 1.
     */
    private void setExtensionSpeed(double speed)
    {
        extensionMotor.set(ControlMode.PercentOutput, speed);
    }

    /**
     * The method to extend the Arm. 
     * Arm extends at half power (0.5).
     */
    public void extendArm()
    {
        setExtensionSpeed(0.5);
    }

    /**
     * The method to retract the Arm.
     * Arm retracts at half power (-0.5).
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
}