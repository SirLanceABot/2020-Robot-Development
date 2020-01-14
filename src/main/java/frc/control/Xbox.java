package frc.control;


import edu.wpi.first.wpilibj.Joystick;


public class Xbox extends Joystick
{
    public enum Button
    {
        kA(1), kB(2), kX(3), kY(4), kLB(5), kRB(6), kBack(7), kStart(8), kLeftStick(9), kRightStick(10);

        public final int value;

        private Button(int value)
        {
            this.value = value;
        }
    }

    public enum Axis
    {
        kLeftX(0), kLeftY(1), kLeftTrigger(2), kRightTrigger(3), kRightX(4), kRightY(5);

        public final int value;

        private Axis(int value)
        {
            this.value = value;
        }
    }

    public enum AxisScale
    {
        kLinear, kSquared, kCubed;
    }

    private final double DEFAULT_DEADZONE = 0.1;
    private final double DEFAULT_MAX_OUTPUT = 1.0;
    private final boolean DEFAULT_IS_FLIPPED = false;
    private final AxisScale DEFAULT_AXIS_SCALE = AxisScale.kLinear;

    private double[] axisDeadzone = new double[6];
    private double[] axisMaxOutput = new double[6];
    private boolean[] axisIsFlipped = new boolean[6];
    private AxisScale[] axisScale = new AxisScale[6];

    public Xbox(int port)
    {
        super(port);

        for(int index = 0; index <= 5; index++)
        {
            axisDeadzone[index] = DEFAULT_DEADZONE;
            axisMaxOutput[index] = DEFAULT_MAX_OUTPUT;
            axisIsFlipped[index] = DEFAULT_IS_FLIPPED;
            axisScale[index] = DEFAULT_AXIS_SCALE;
        }
    }

    @Override
    public double getRawAxis(int axis)
    {
        double value = super.getRawAxis(axis);

        if(axisIsFlipped[axis])
        {
            value *= -1;
        }
        if(Math.abs(value) <= axisDeadzone[axis])
        {
            value = 0.0;
        }
        else
        {
            switch(axisScale[axis])
            {
                case kLinear:
                    value = axisMaxOutput[axis] / (1.0 - axisDeadzone[axis]) * (value - axisDeadzone[axis] * Math.signum(value));
                    break;
                case kSquared:
                    value = axisMaxOutput[axis] * Math.signum(value) / Math.pow((1.0 - axisDeadzone[axis]), 2) * Math.pow((value - axisDeadzone[axis] * Math.signum(value)), 2);
                    break;
                case kCubed:
                value = axisMaxOutput[axis] / Math.pow((1.0 - axisDeadzone[axis]), 3) * Math.pow((value - axisDeadzone[axis] * Math.signum(value)), 3);
                    break;
            }
        }
        return value;
    }
    
    public double getRawAxis(Axis axis)
    {
        return getRawAxis(axis.value);
    }

    public boolean getRawButton(Button button)
    {
        return super.getRawButton(button.value);
    }

    public void setAxisDeadzone(Axis axis, double axisDeadzone)
    {
        axisDeadzone = Math.abs(axisDeadzone);
        axisDeadzone = Math.min(1.0, axisDeadzone);

        this.axisDeadzone[axis.value] = axisDeadzone;
    }

    public void setAxisMaxOutput(Axis axis, double axisMaxOutput)
    {
        axisMaxOutput = Math.abs(axisMaxOutput);
        axisMaxOutput = Math.min(axisMaxOutput, 1.0);

        this.axisMaxOutput[axis.value] = axisMaxOutput;
    }
}