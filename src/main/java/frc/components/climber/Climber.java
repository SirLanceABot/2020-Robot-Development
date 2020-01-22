// Import all the code involved in the robot's components.
package frc.components.climber;

// Import material to implement Talons and controls.
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climber
{
    // Create private instance variables.
    private static TalonSRX extensionMotor = new TalonSRX(0);
    private static TalonSRX winchMotorMaster = new TalonSRX(1);
    //TODO: verify motor controllers
    private static Climber instance = new Climber();

    // Get instances of the Arm and Winch subclasses.
    private static Winch winch = Winch.getInstance();
    private static Arm arm = Arm.getInstance();

    // Create constants
    //TODO: verify actual buttons
    private static final double MINIMUM_HEIGHT = 0.0;
    private static final double MAXIMUM_HEIGHT = 50.0;
    private static final boolean IS_RAISE_CLIMBER_BUTTON_PRESSED = false;
    private static final boolean IS_LOWER_WINCH_BUTTON_PRESSED = false;
    private static final boolean IS_RAISE_WINCH_BUTTON_PRESSED = false;

    /**
     * The constructor for the Climber class. 
     */
    private Climber()
    {
        
    }

    /**
     * The method to retrieve the instance of a Climber.
     * @return instance 
     */
    public Climber getInstance()
    {
        return instance;
    }

    public void lowerClimber()
    {
        double currentPosition = Arm.getEncoderPosition;
        while(currentPosition > MINIMUM_HEIGHT)
        {
            setMotorSpeed = -.05;
        }
    }
    
    public void raiseClimber()
    {
        double currentPosition = Arm.getEncoderPosition;
        while(currentPosition < MAXIMUM_HEIGHT)
        {
            setMotorSpeed = .05;
        }
    }

    public void lowerWinch()
    {

    }

    public void raiseWinch()
    {

    }
}