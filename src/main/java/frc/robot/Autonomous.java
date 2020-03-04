package frc.robot;

import frc.autonomous.AutonomousBuilder;
import frc.autonomous.AutonomousExecuter;
import frc.components.powercellsupervisor.shuttle.Shuttle;
import edu.wpi.first.wpilibj.Relay;

public class Autonomous
{
    private static final String className = new String("[Autonomous]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private static Autonomous instance = new Autonomous();
    private static AutonomousExecuter autonomousExecuter = AutonomousExecuter.getAutonomousExecuter();
    private static AutonomousBuilder autonomousBuilder = AutonomousBuilder.getInstance();
    private static Shuttle shuttle = Shuttle.getInstance();
    private static Relay led = new Relay(0);
    /**
     * The constructor for the Autonomous class. 
     */
    private Autonomous()
    {
        System.out.println(className + " : Constructor Started");
        
        System.out.println(className + ": Constructor Finished");
    }

    /**
     * The method to retrieve the instance of Autonomous.
     * @return instance 
     */
    public static Autonomous getInstance()
    {
        return instance;
    }

    public void init()
    {
        autonomousBuilder.buildCommandList();
    }

    public void periodic()
    {
        led.set(Relay.Value.kForward);
        shuttle.runFSM();
        autonomousExecuter.executeAuto();   
    }
}