package frc.components.motor;

public abstract class Motor
{
    public enum MyNeutralMode
    {
        kBrake, kCoast;
    }

    abstract void setInverted(boolean isInverted);
    abstract void setReverseSoftLimitEnabled(boolean isEnabled);
    abstract void setForwardSoftLimitEnabled(boolean isEnabled);
    abstract void setNeutralMode(MyNeutralMode mode);
    abstract void setStatorCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime);
    abstract void setSupplyCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime);
    abstract void setOpenLoopRamp(double seconds);
    abstract void getSuper();

    void configMotor(boolean setInverted, boolean softLimitEnabled)
    {
        
    }
}