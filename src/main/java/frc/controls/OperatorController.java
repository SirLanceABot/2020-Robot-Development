package frc.controls;

public class OperatorController extends Logitech
{
    private static final int OPERATOR_CONTROLLER_PORT = 1;

    private static OperatorController instance = new OperatorController(OPERATOR_CONTROLLER_PORT);

    private OperatorController(int port)
    {
        super(port);
        System.out.println(this.getClass().getName() + " : constructor started");

        setAxisSettings(Axis.kXAxis, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kYAxis, 0.1, 0.0, 1.0, true, AxisScale.kLinear);
        setAxisSettings(Axis.kZAxis, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kSlider, 0.1, 0.0, 1.0, false, AxisScale.kLinear);

        System.out.println(this.getClass().getName() + " : constructor finished");
    }

    public static OperatorController getInstance()
    {
        return instance;
    }
}