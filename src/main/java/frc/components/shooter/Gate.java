package frc.components.shooter;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Gate
{   
    private static final int WRIST_SOLENOID_EXTEND = 0;
    private static final int WRIST_SOLENOID_RETRACT = 1;

    private DoubleSolenoid gateSolenoid = new DoubleSolenoid(0, 1);
    private static Gate instance = new Gate();

    private Gate()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    /**
     * @return the instance variable for the gate to be used
     */
    public static Gate getInstance()
    {
        return instance;
    }

    /**
     * opens the gate (solenoid retracts)
     */
    public void openGate()
    {
        gateSolenoid.set(Value.kReverse);
    }

    /**
     * closes the gate (solenoid extends)
     */
    public void closeGate()
    {
        gateSolenoid.set(Value.kForward);
    }

    /**
     * @return the current position of the solenoid ()
     */
    public DoubleSolenoid.Value getSolenoidPosition()
    {
        return gateSolenoid.get();
    }

}