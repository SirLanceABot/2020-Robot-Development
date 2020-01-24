// Import all the code involved in the robot's components.
package frc.components.climber;

// Import all the material from the Arm and Winch subclasses to be packaged together in this class.
import frc.components.climber.Arm;
import frc.components.climber.Winch;

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

    // Get private instances of the Arm and Winch subclasses.
    private static Winch winch = Winch.getInstance();
    private static Arm arm = Arm.getInstance();

    // Create constants
    //TODO: verify actual minimum and maximum heights
    //TODO: verify actual buttons
    private static final double MINIMUM_HEIGHT = 0.0;
    private static final double MAXIMUM_HEIGHT = 50.0;
    private static final boolean IS_LOWER_CLIMBER_BUTTON_PRESSED = false;
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

    /**
     * The method to lower the Climber.
     */
    public void lowerClimber()
    {
        double currentPosition = arm.getEncoderPosition();
        if(currentPosition > MINIMUM_HEIGHT)
        {
            //setMotorSpeed = -.05;
        }
        else
        {
            System.out.println("Cannot lower the climber. It is already at the minimum height.");
        }
    }
    
    /**
     * The method to raise the Climber.
     */
    public void raiseClimber()
    {
        double currentPosition = arm.getEncoderPosition();
        if(currentPosition < MAXIMUM_HEIGHT)
        {
            //setMotorSpeed = .05;
        }
        else
        {
            System.out.println("Cannot raise the climber. It is already at the maximum height.");
        }
    }

    /**
     * The method to lower the Winch.
     */
    public void lowerWinch()
    {
        double currentPosition = winch.getEncoder();
        if(currentPosition > MINIMUM_HEIGHT)
        {
            //setMotorSpeed = -.05;
        }
        else
        {
            System.out.println("Cannot lower the winch. It is already at the minimum height.");
        }
    }

    /**
     * The method to raise the Winch.
     */
    public void raiseWinch()
    {
        double currentPosition = winch.getEncoder();
        if(currentPosition < MAXIMUM_HEIGHT)
        {
            //setMotorSpeed = .05;
        }
        else
        {
            System.out.println("Cannot raise the winch. It is already at the maximum height.");
        }
    }
}