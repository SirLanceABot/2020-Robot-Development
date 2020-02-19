package frc.robot;

public class Autonomous
{
    private static final String className = new String("[Autonomous]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private static Autonomous instance = new Autonomous();

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

    }

    public void periodic()
    {

    }
}