package frc.robot;

public class Autonomous
{
    private static Autonomous instance = new Autonomous();

    /**
     * The constructor for the Autonomous class. 
     */
    private Autonomous()
    {

    }

    /**
     * The method to retrieve the instance of Autonomous.
     * @return instance 
     */
    public static Autonomous getInstance()
    {
        return instance;
    }
}