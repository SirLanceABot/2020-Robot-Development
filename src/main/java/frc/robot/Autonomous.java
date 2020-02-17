package frc.robot;

public class Autonomous
{
    private static Autonomous instance = new Autonomous();

    /**
     * The constructor for the Autonomous class. 
     */
    private Autonomous()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        
        System.out.println(this.getClass().getName() + ": Finished Constructing");
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