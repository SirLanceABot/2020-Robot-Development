package frc.controls;

/**
 * Customized Xbox instance used for driving the robot
 * @author Elliot Measel
 */
public class DriverController extends Xbox
{
    // the one and only instance of driver controller
    private static DriverController instance = new DriverController(0); 

    /**
     * Private constructor for driver controller
     */
    private DriverController(int port)
    {
        super(port);
        System.out.println(this.getClass().getName() + " : constructor started");

        setAxisSettings(Axis.kLeftX, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kLeftY, 0.1, 0.0, 1.0, true, AxisScale.kLinear);

        setAxisSettings(Axis.kRightX, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kRightY, 0.1, 0.0, 1.0, true, AxisScale.kLinear);

        setAxisSettings(Axis.kLeftTrigger, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kRightTrigger, 0.1, 0.0, 1.0, false, AxisScale.kLinear);

        

        System.out.println(this.getClass().getName() + " : constructor finished");
    }

    /**
     * Public method to return the one instance of the driver controller
     * @return
     */
    public static DriverController getInstance()
    {
        return instance;
    }
}