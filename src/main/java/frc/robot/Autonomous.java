package frc.robot;

import frc.autonomous.AutonomousBuilder;
import frc.autonomous.AutonomousExecuter;
import frc.components.powercellsupervisor.shuttle.Shuttle;

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
        shuttle.runFSM();
        autonomousExecuter.executeAuto();   
    }
}