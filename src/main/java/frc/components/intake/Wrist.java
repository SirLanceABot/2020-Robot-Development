package frc.components.intake;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Class for controlling the Wrist of the Intake system.
 * @author Darren Fife
 */
public class Wrist
{
    // Constants
    private static final int WRIST_SOLENOID_EXTEND_PORT = 0;
    private static final int WRIST_SOLENOID_RETRACT_PORT = 1;

    private static DoubleSolenoid wristSolenoid = new DoubleSolenoid(WRIST_SOLENOID_EXTEND_PORT, WRIST_SOLENOID_RETRACT_PORT);

    private static Wrist instance = new Wrist();

    private Wrist()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    protected static Wrist getInstance()
    {
        return instance;
    }

    /**
     * Lowers the Wrist on the Intake system.
     */
    public void lower()
    {
        setPneumatics(DoubleSolenoid.Value.kForward);
    }

    /**
     * Raises the Wrist on the Intake system.
     */
    public void raise()
    {
        setPneumatics(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Sets the pneumatics position of the Wrist on the Intake system.
     * <p>DoubleSolenoid.Value.kReverse raises the Wrist.
     * <p>DoubleSolenoid.Value.kForward lowers the Wirst.
     * @param position The position of the cylinder.
     */
    private static void setPneumatics(DoubleSolenoid.Value position)
    {
        wristSolenoid.set(position);
    }
}