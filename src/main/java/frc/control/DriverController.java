package frc.control;

public class DriverController extends Xbox
{
    private static DriverController driverController = new DriverController(); 

    private DriverController()
    {
        super(0);
        setAxisIsFlipped(Xbox.Axis.kLeftY, true);
        setAxisIsFlipped(Xbox.Axis.kRightY, true);
    }

    public static DriverController getInstance()
    {
        return driverController;
    }
}