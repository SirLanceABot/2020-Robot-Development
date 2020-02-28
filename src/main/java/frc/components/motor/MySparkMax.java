package frc.components.motor;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MySparkMax implements Motor
{
    private CANSparkMax motor;

    public MySparkMax(int port)
    {
        motor = new CANSparkMax(port, MotorType.kBrushless);
        motor.restoreFactoryDefaults();
    }

    public void setInverted(boolean isInverted)
    {
        motor.setInverted(isInverted);
    }

    public void setReverseSoftLimitEnabled(boolean isEnabled)
    {
        
    }

    public void setForwardSoftLimitEnabled(boolean isEnabled)
    {

    }

    public void setNeutralMode(MyNeutralMode mode)
    {

    }

    public void setStatorCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {

    }

    public void setSupplyCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {

    }

    public void setOpenLoopRamp(double seconds)
    {

    }

    public void getSuper()
    {

    }
}