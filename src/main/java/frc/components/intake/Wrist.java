package frc.components.intake;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 * Class for controlling the Wrist of the Intake system.
 * @author Darren Fife
 */
public class Wrist
{
    // Constants
    private static enum WristState
    {
        kUp, kLowering, kDown, kRaising
    };

    // TODO: Change these if the solenoids are going in the wrong direction
    private static final int WRIST_SOLENOID_LEFT_EXTEND_PORT = 1;
    private static final int WRIST_SOLENOID_LEFT_RETRACT_PORT = 0;
    private static final int WRIST_SOLENOID_RIGHT_EXTEND_PORT = 2;
    private static final int WRIST_SOLENOID_RIGHT_RETRACT_PORT = 3;

    private static DoubleSolenoid wristSolenoidLeft = new DoubleSolenoid(WRIST_SOLENOID_LEFT_EXTEND_PORT, WRIST_SOLENOID_LEFT_RETRACT_PORT);
    private static DoubleSolenoid wristSolenoidRight = new DoubleSolenoid(WRIST_SOLENOID_RIGHT_EXTEND_PORT, WRIST_SOLENOID_RIGHT_RETRACT_PORT);

    private WristState wristState = WristState.kUp;

    private static Timer timer;
    private static Wrist instance = new Wrist();

    private Wrist()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        timer = new Timer();
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    protected static Wrist getInstance()
    {
        return instance;
    }

    /**
     * Raises the Wrist on the Intake system.
     */
    public void raise()
    {
        if(wristState !=  WristState.kUp)
        {
            timer.reset();
            setPneumatics(DoubleSolenoid.Value.kReverse);
            wristState = WristState.kLowering;
            timer.start();
        }
    }

    /**
     * 
     */
    public boolean isUp()
    {
        return wristState == WristState.kUp;
    }

    /**
     * Lowers the Wrist on the Intake system.
     */
    public void lower()
    {
        if(wristState != WristState.kDown)
        {
            timer.reset();
            setPneumatics(DoubleSolenoid.Value.kForward);
            wristState = WristState.kRaising;
            timer.start();
        }
    }

    /**
     * 
     */
    public boolean isDown()
    {
        
        return true;
    }

    /**
     * Sets the pneumatics position of the Wrist on the Intake system.
     * <p>DoubleSolenoid.Value.kReverse raises the Wrist.
     * <p>DoubleSolenoid.Value.kForward lowers the Wirst.
     * @param position The position of the cylinder.
     */
    private static void setPneumatics(DoubleSolenoid.Value position)
    {
        wristSolenoidLeft.set(position);
        wristSolenoidRight.set(position);
    }
}