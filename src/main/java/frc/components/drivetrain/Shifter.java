package frc.components.drivetrain;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Class for controlling the gear Shifter of the Drivetrain.
 * @author Darren Fife
 */
// TODO: Test if the system works with only one solenoid.
public class Shifter
{
    // TODO: Constants - Change these if the solenoids are going in the wrong direction
    private static final int SHIFTER_SOLENOID_EXTEND_PORT = 1;
    private static final int SHIFTER_SOLENOID_RETRACT_PORT = 0;
    // private static final int SHIFTER_SOLENOID_RIGHT_EXTEND_PORT = 2;
    // private static final int SHIFTER_SOLENOID_RIGHT_RETRACT_PORT = 3;

    private static DoubleSolenoid shifterSolenoid = new DoubleSolenoid(SHIFTER_SOLENOID_EXTEND_PORT, SHIFTER_SOLENOID_RETRACT_PORT);
    // private static DoubleSolenoid shifterSolenoidRight = new DoubleSolenoid(SHIFTER_SOLENOID_RIGHT_EXTEND_PORT, SHIFTER_SOLENOID_RIGHT_RETRACT_PORT);

    private static Shifter instance = new Shifter();

    private Shifter()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    protected static Shifter getInstance()
    {
        return instance;
    }

    /**
     * Shifts the dog gear into high gear (7 feet per second).
     */
    public void highGear()
    {
        setPneumatics(DoubleSolenoid.Value.kForward);
    }

    /**
     * Shifts the dog gear into low gear (7 feet per second).
     */
    public void lowGear()
    {
        setPneumatics(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Sets the pneumatics position of the Shifter of the Drivetrain.
     * <p>DoubleSolenoid.Value.kReverse sets the gear into low gear.
     * <p>DoubleSolenoid.Value.kForward sets the gear into high gear.
     * @param position The position of the cylinder.
     */
    private static void setPneumatics(DoubleSolenoid.Value position)
    {
        shifterSolenoid.set(position);
        // shifterSolenoidRight.set(position);
    }
}