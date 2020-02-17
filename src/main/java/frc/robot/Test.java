package frc.robot;

import frc.controls.DriverController;

/**
 * @author Elliot Measel
 * class for the test mode on the robot
 */
public class Test
{
    private static DriverController driverController = DriverController.getInstance();

    private static Test instance = new Test();

    /**
     * private constructor for test class
     */
    private Test()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        
        System.out.println(this.getClass().getName() + ": Finished Constructing");
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