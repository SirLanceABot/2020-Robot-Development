// Import all the code involved in the robot's components.
package frc.components.climber;

// Import all the material from the Arm and Winch subclasses to be packaged together in this class.
import frc.components.climber.Arm;
import frc.components.climber.Winch;
import frc.controls.DriverController;

// Import material to implement Talons and controls.
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climber
{
    private static final String className = new String("[Climber]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    // Create private instance variables.
    private static TalonSRX extensionMotor = new TalonSRX(0);
    private static TalonSRX winchMotorMaster = new TalonSRX(1);
    //TODO: verify motor controllers
    private static Climber instance = new Climber();
    private static DriverController driverController = DriverController.getInstance();

    // Get private instances of the Arm and Winch subclasses.
    private static Winch winch = Winch.getInstance();
    private static Arm arm = Arm.getInstance();

    // Create constants
    //TODO: verify actual buttons
    private static final int RAISE_ARM_BUTTON = 0;
    private static final int LOWER_ARM_BUTTON = 1;
    private static final int RAISE_WINCH_BUTTON = 2;
    private static final int LOWER_WINCH_BUTTON = 3;

    /**
     * The constructor for the Climber class. 
     */
    private Climber()
    {
        System.out.println(className + " : Constructor Started");

        System.out.println(className + ": Constructor Finished");
    }

    /**
     * The method to retrieve the instance of a Climber.
     * @return instance 
     */
    public static Climber getInstance()
    {
        return instance;
    }
    
    /**
     * The method to run the Arm and Winch subclasses under the Climber.
     */
    public void run()
    {
        if(driverController.getRawButtonPressed(LOWER_ARM_BUTTON))
        {
            arm.retractArm(-0.5);
        }
        else if(driverController.getRawButtonPressed(RAISE_ARM_BUTTON))
        {
            arm.extendArm(0.5);
        }
        else if(driverController.getRawButtonPressed(LOWER_WINCH_BUTTON))
        {
            winch.lowerWinch(-0.5);
        }
        else if(driverController.getRawButtonPressed(RAISE_WINCH_BUTTON))
        {
            winch.raiseWinch(0.5);
        }
        else
        {
            arm.stopArm();
            winch.stopWinch();
        }
    }
}