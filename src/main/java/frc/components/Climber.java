// Import all the code involved in the robot's components.
package frc.components;

// Import material to implement Talons and controls.
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climber
{
    // Create private instance variables for the instance of the class
    // and the three motors involved in the climbing mechanism.
    // The private instance variable for the instance must come last in these declarations.
    private static TalonSRX extensionMotor = new TalonSRX(0);
    private static TalonSRX winchMotorMaster = new TalonSRX(1);
    private static TalonSRX winchMotorSlave = new TalonSRX(2);
    //TODO: verify motor controllers
    private static Climber instance = new Climber();
    
    // Create a private constructor for the Climber() class,
    // which resets the three motors to their default factory settings
    // makes the slave winch motor work in tandem with the master winch motor.

    /**
     * The constructor for the Climber class. 
     */
    private Climber()
    {
        // Reset the motors to their default factory settings.
        extensionMotor.configFactoryDefault();
        winchMotorMaster.configFactoryDefault();
        winchMotorSlave.configFactoryDefault();

        // Make the winch motor slave follow the winch motor master.
        winchMotorSlave.follow(winchMotorMaster);
    }

    /**
     * The method to retrieve the instance of a Climber.
     * @return instance 
     */
    public Climber getInstance()
    {
        return instance;
    }

    // Are we setting the climber speed from a trigger on the joystick,
    // or is the speed an automatic thing that is triggered just when the climber moves?
    // Do all the motors run at the same speed?
    // Why would we need to adjust the speed of the climber?

    /**
     * Sets the speed of the extensionMotor.
     * @param speed The speed at which the Climber extends. Values are from -1 to 1.
     */
    private void setExtensionSpeed(double speed)
    {
        extensionMotor.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Sets the speed of the winchMotorMaster.
     * @param speed The speed at which the winch spools the rope. Values are from -1 to 1.
     */
    private void setWinchSpeed(double speed)
    {
        winchMotorMaster.set(ControlMode.PercentOutput, speed);
    }

    /**
     * The method to extend the Climber. 
     * Climber extends at half power (0.5).
     */
    public void extendClimber()
    {
        setExtensionSpeed(0.5);
    }

    /**
     * The method to retract the Climber.
     * Climber retracts at half power (-0.5).
     */
    public void retractClimber()
    {
        setExtensionSpeed(-0.5);
    }

    /**
     * The method to stop the extension or retraction of the Climber.
     */
    public void stopExtender()
    {
        setExtensionSpeed(0.0);
    }

}