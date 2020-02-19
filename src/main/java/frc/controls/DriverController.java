package frc.controls;

import frc.robot.Port;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Customized Xbox instance used for driving the robot
 * 
 * @author Elliot Measel
 */
public class DriverController extends Xbox
{
    private static final String className = new String("[DriverController]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    public enum ButtonAction
    {
        kIntakeOn(Button.kRB),
        kIntakeUp(Button.kRightStick),
        kIntakeDown(Button.kLeftStick),
        kWalk(Button.kLB);

        public final Button button;

        private ButtonAction(Button button)
        {
            this.button = button;
        }
    }

    public enum AxisAction
    {
        kMove(Axis.kLeftY),
        kRotate(Axis.kRightX);

        public final Axis axis;

        private AxisAction(Axis axis)
        {
            this.axis = axis;
        }
    }

    public class RumbleEvent
    {
        public double startTime;
        public double duration;
        public double leftPower;
        public double rightPower;

        public RumbleEvent(double startTime, double duration, double leftPower, double rightPower)
        {
            this.startTime = startTime;
            this.duration = duration;
            this.leftPower = leftPower;
            this.rightPower = rightPower;
        }
    }

    private ArrayList<RumbleEvent> rumbleEvents = new ArrayList<RumbleEvent>();
    private int rumbleCounter = 0;

    private DriverStation driverStation = DriverStation.getInstance();

    // the one and only instance of driver controller
    private static DriverController instance = new DriverController(Port.Controller.DRIVER); 

    /**
     * Private constructor for driver controller
     */
    private DriverController(int port)
    {
        super(port);

        System.out.println(className + " : Constructor Started");

        setAxisSettings(Axis.kLeftX, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kLeftY, 0.1, 0.0, 1.0, true, AxisScale.kLinear);

        setAxisSettings(Axis.kRightX, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kRightY, 0.1, 0.0, 1.0, true, AxisScale.kLinear);

        setAxisSettings(Axis.kLeftTrigger, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kRightTrigger, 0.1, 0.0, 1.0, false, AxisScale.kLinear);

        createRumbleEvent(60.0, 1.0, 0.5, 0.5);
        createRumbleEvent(30.0, 2.0, 0.75, 0.75);
        createRumbleEvent(10.0, 1.0, 1.0, 1.0);
        createRumbleEvent(5.0, 0.25, 1.0, 1.0);
        createRumbleEvent(4.0, 0.25, 1.0, 1.0);
        createRumbleEvent(3.0, 0.25, 1.0, 1.0);
        createRumbleEvent(2.0, 0.25, 1.0, 1.0);
        createRumbleEvent(1.0, 0.25, 1.0, 1.0);

        System.out.println(className + ": Constructor Finished");
    }

    /**
     * Public method to return the one instance of the driver controller
     * @return
     */
    public static DriverController getInstance()
    {
        return instance;
    }

    public void createRumbleEvent(double startTime, double duration, double leftPower, double rightPower)
    {
        rumbleEvents.add(new RumbleEvent(startTime, duration, leftPower, rightPower));
    }

    public void checkRumbleEvent()
    {
        if (rumbleEvents.size() > rumbleCounter)
        {
            double matchTime = driverStation.getMatchTime();
            double startTime = rumbleEvents.get(rumbleCounter).startTime;
            double duration = rumbleEvents.get(rumbleCounter).duration;

            if (startTime >= matchTime && matchTime >= startTime - duration)
            {
                setRumble(RumbleType.kLeftRumble, rumbleEvents.get(rumbleCounter).leftPower);
                setRumble(RumbleType.kRightRumble, rumbleEvents.get(rumbleCounter).rightPower);
            }
            else if (matchTime < startTime - duration)
            {
                rumbleCounter++;
                setRumble(RumbleType.kLeftRumble, 0.0);
                setRumble(RumbleType.kRightRumble, 0.0); 
            }
        }
    }

    @Deprecated
    public double getRawAxis(Axis axis)
    {
        return super.getRawAxis(axis);
    }

    @Deprecated
    public boolean getRawButton(Button button)
    {
        return super.getRawButton(button);
    }

    public boolean getAction(ButtonAction buttonAction)
    {
        return getRawButton(buttonAction.button);
    }

    public double getAction(AxisAction axisAction)
    {
        return getRawAxis(axisAction.axis);
    }

    public void resetRumbleCounter()
    {
        rumbleCounter = 0;
    }
}