package frc.controls;

import frc.robot.Port;

public class OperatorController extends Logitech
{
    private static final String className = new String("[OperatorController]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    public enum ButtonAction
    {
        kShoot(Button.kTrigger),
        kAutoAim(Button.kHandleSide),
        kTurretOverride(Button.kHandleBottomLeft),
        kShooterOverride(Button.kInnerTop),
        kShuttleOverride(Button.kOuterTop),
        kClimbOverride(Button.kOuterMiddle),
        kArmUp(Button.kInnerMiddle),
        kArmDown(Button.kInnerBottom),
        kWinch(Button.kOuterBottom),
        kOnTarget(Button.kHandleBottomRight),
        kOffTarget(Button.kHandleBottomLeft);
        ;
        public final Button button;

        private ButtonAction(Button button)
        {
           this.button = button;
        }
    }

    public enum AxisAction
    {
        kTurret(Axis.kZAxis),
        kShroud(Axis.kYAxis),
        //kShuttle(Axis.kYAxis),
        kShooterPower(Axis.kSlider);

        public final Axis axis;

        private AxisAction(Axis axis)
        {
           this.axis = axis;
        }
    }

    private static OperatorController instance = new OperatorController(Port.Controller.OPERATOR);

    private OperatorController(int port)
    {
        super(port);

        System.out.println(className + " : Constructor Started");

        setAxisSettings(Axis.kXAxis, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kYAxis, 0.1, 0.0, 1.0, true, AxisScale.kLinear);
        setAxisSettings(Axis.kZAxis, 0.1, 0.0, 1.0, false, AxisScale.kLinear);
        setAxisSettings(Axis.kSlider, 0.1, 0.0, 1.0, true, AxisScale.kLinear);

        System.out.println(className + ": Constructor Finished");
    }

    public static OperatorController getInstance()
    {
        return instance;
    }

    @Deprecated
    public boolean getRawButton(Button button)
    {
        return super.getRawButton(button);
    }

    @Deprecated
    public double getRawAxis(Axis axis)
    {
        return super.getRawAxis(axis);
    }

    public boolean getAction(ButtonAction buttonAction)
    {
        return getRawButton(buttonAction.button);
    }

    public double getAction(AxisAction axisAction)
    {
        return getRawAxis(axisAction.axis);
    }
}