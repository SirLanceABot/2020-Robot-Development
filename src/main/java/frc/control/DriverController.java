package frc.control;

/**
 * Customized Xbox instance used for driving the robot
 * @author Elliot Measel
 */
public class DriverController extends Xbox
{
    // the one and only instance of driver controller
    private static DriverController driverController = new DriverController(); 

    /**
     * Private constructor for driver controller
     */
    private DriverController()
    {
        super(0);
        setAxisIsFlipped(Xbox.Axis.kLeftY, true);
        setAxisIsFlipped(Xbox.Axis.kRightY, true);
    }

    /**
     * Public method to return the one instance of the driver controller
     * @return
     */
    public static DriverController getInstance()
    {
        return driverController;
    }
}