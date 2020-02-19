package frc.robot;

import frc.controls.DriverController;

/**
 * @author Elliot Measel
 * class for the test mode on the robot
 */
public class Test
{
    private static final String className = new String("[Test]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private static DriverController driverController = DriverController.getInstance();

    private static Test instance = new Test();

    /**
     * private constructor for test class
     */
    private Test()
    {
        System.out.println(className + " : Constructor Started");
        
        System.out.println(className + ": Constructor Finished");
    }

    public static Test getInstance()
    {
        return instance;
    }

    public void init()
    {

    }

    public void periodic()
    {
        driverController.resetRumbleCounter();
    }
}