package frc.control;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Class for creating a Logitech Controller object
 * @author Ishaan Gupta and Darren Fife
 */
public class Logitech extends Joystick
{
    public enum Button
    {
        kTrigger(1), kHandleSide(2), kHandleBottomLeft(3), kHandleBottomRight(4), kHandleTopLeft(5), kHandleTopRight(6), 
        kOuterTop(7), kInnerTop(8), kOuterMiddle(9), kInnerMiddle(10), kOuterBottom(11), kInnerBottom(12);

        public final int value;

        private Button(int value)
        {
            this.value = value;
        }
    }

    public enum Axis
    {
        kXAxis(0), kYAxis(1), kZAxis(2), kSlider(3);

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

    private double[] axisDeadzone = new double[4];
    private double[] axisMaxOutput = new double[4];
    private boolean[] axisIsFlipped = new boolean[4];
    private AxisScale[] axisScale = new AxisScale[4];
    private Button[] buttons = new Button[11];

    private Logitech(int port)
    {
        super(port);

        for(int index = 0; index <= 3; index++)
            {
                axisDeadzone[index] = DEFAULT_DEADZONE;
                axisMaxOutput[index] = DEFAULT_MAX_OUTPUT;
                axisIsFlipped[index] = DEFAULT_IS_FLIPPED;
                axisScale[index] = DEFAULT_AXIS_SCALE;
            }
    }

    /**
     * Returns the value of the specified axis
     * @param axis
     */
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

    /**
     * @return the value of the specified axis
     * @param axis
     */
    public double getRawAxis(Axis axis)
    {
        return getRawAxis(axis.value);
    }

    /**
     * @return whether or not the specified is button is being pressed
     * @param button
     */
    public boolean getRawButton(Button button)
    {
        return super.getRawButton(button.value);
    }

    /**
     * Used to set the deadzone of the specified axis
     * @param axis
     * @param axisDeadzone
     */
    public void setAxisDeadzone(Axis axis, double axisDeadzone)
    {
        axisDeadzone = Math.abs(axisDeadzone);
        axisDeadzone = Math.min(1.0, axisDeadzone);

        this.axisDeadzone[axis.value] = axisDeadzone;
    }

    /**
     * Used to set the maximum output of the specified axis
     * @param axis
     * @param axisMaxOutput
     */
    public void setAxisMaxOutput(Axis axis, double axisMaxOutput)
    {
        axisMaxOutput = Math.abs(axisMaxOutput);
        axisMaxOutput = Math.min(axisMaxOutput, 1.0);

        this.axisMaxOutput[axis.value] = axisMaxOutput;
    }

    /**
     * Used to flip the output value of the specified axis
     * @param axis
     * @param axisIsFlipped
     */
    public void setAxisFlipped(Axis axis, boolean axisIsFlipped)
    {
        this.axisIsFlipped[axis.value] = axisIsFlipped;
    }

    /**
     * Used to set the scale of the specified axis
     * (kLinear, kSquared, kCubed)
     * @param axis
     * @param axisScale
     */
    public void setAxisScale(Axis axis, AxisScale axisScale)
    {
        this.axisScale[axis.value] = axisScale;
    }

    @Override
    public String toString()
    {
        String str = "";
        
        for(int i = 1; i <= 12; i++)
        switch(buttons[i])
        {
            case kTrigger:
                if(getRawButton(Button.kTrigger))
                {
                    System.out.println("Trigger is pressed");
                }
                break;
            case kHandleSide:
                if(getRawButton(Button.kHandleSide))
                {
                    System.out.println("Side Button is pressed");
                }
                break;
            case kHandleBottomLeft:
                if(getRawButton(Button.kHandleBottomLeft))
                {
                    System.out.println("Bottom Left button on handle is pressed");
                }
                break;
            case kHandleBottomRight:
                if(getRawButton(Button.kHandleBottomRight))
                {
                    System.out.println("Bottom right button on handle is pressed");
                }
                break;
            case kHandleTopLeft:
                if(getRawButton(Button.kHandleTopLeft))
                {
                    System.out.println("Top Left button on handle is pressed");
                }
                break;
            case kHandleTopRight:
                if(getRawButton(Button.kHandleTopRight))
                {
                    System.out.println("Top Right button on handle is pressed");
                }
                break;
            case kOuterTop:
                if(getRawButton(Button.kOuterTop))
                {
                    System.out.println("Outer Top button is pressed");
                }
                break;
            case kInnerTop:
                if(getRawButton(Button.kInnerTop))
                {
                    System.out.println("Inner Top button is pressed");
                }
                break;
            case kOuterMiddle:
                if(getRawButton(Button.kOuterMiddle))
                {
                    System.out.println("Outer Middle button is pressed");
                }
                break;
            case kInnerMiddle:
                if(getRawButton(Button.kInnerMiddle))
                {
                    System.out.println("Inner Middle button is pressed");
                }
                break;
            case kOuterBottom:
                if(getRawButton(Button.kOuterBottom))
                {
                    System.out.println("Outer Bottom button is pressed");
                }
                break;
            case kInnerBottom:
                if(getRawButton(Button.kInnerBottom))
                {
                    System.out.println("Inner Bottom button is pressed");
                }
                break;
        }

        return str;
    }
}