package frc.controls;

public class OperatorController extends Logitech
{
    public class AxisSettings
    {
        public double axisDeadzone;
        public double axisMinOutput;
        public double axisMaxOutput;
        public boolean axisIsFlipped;
        public AxisScale axisScale;

        public AxisSettings()
        {
            this(0.1, 0.0, 1.0, false, AxisScale.kLinear);
        }

        public AxisSettings(double axisDeadzone, double axisMinOutput, double axisMaxOutput, boolean axisIsFlipped, AxisScale axisScale)
        {
            this.axisDeadzone = axisDeadzone;
            this.axisMinOutput = axisMinOutput;
            this.axisMaxOutput = axisMaxOutput;
            this.axisIsFlipped = axisIsFlipped;
            this.axisScale = axisScale;
        }
    }

    private static final int OPERATOR_CONTROLLER_PORT = 1;

    private static AxisSettings[] axisSettings = new AxisSettings[4];

    private static OperatorController instance = new OperatorController(OPERATOR_CONTROLLER_PORT);

    private OperatorController(int port)
    {
        super(port);
        axisSettings[Axis.kXAxis.value] = new AxisSettings(0.1, 0.0, 1.0, false, AxisScale.kLinear);
        axisSettings[Axis.kYAxis.value] = new AxisSettings(0.1, 0.0, 1.0, true, AxisScale.kLinear);
        axisSettings[Axis.kZAxis.value] = new AxisSettings(0.1, 0.0, 1.0, false, AxisScale.kLinear);
        axisSettings[Axis.kSlider.value] = new AxisSettings(0.1, 0.0, 1.0, false, AxisScale.kLinear);
        
        setAxisSettings(Axis.kXAxis);
        setAxisSettings(Axis.kYAxis);
        setAxisSettings(Axis.kZAxis);
        setAxisSettings(Axis.kSlider);
    }

    public static OperatorController getInstance()
    {
        return instance;
    }

    public void setAxisSettings(Axis axis)
    {
        setAxisDeadzone(axis, axisSettings[axis.value].axisDeadzone);
        setAxisMinOutput(axis, axisSettings[axis.value].axisMinOutput);
        setAxisMaxOutput(axis, axisSettings[axis.value].axisMaxOutput);
        setAxisFlipped(axis, axisSettings[axis.value].axisIsFlipped);
        setAxisScale(axis, axisSettings[axis.value].axisScale);
    }

    public AxisSettings getAxisSettings(Axis axis)
    {
        return axisSettings[axis.value];
    }
}