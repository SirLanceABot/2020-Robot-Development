package frc.components.motor;

public interface Motor
{
    public enum MyNeutralMode
    {
        kBrake, kCoast;
    }

    void setInverted(boolean isInverted);
    void setReverseSoftLimitEnabled(boolean isEnabled);
    void setForwardSoftLimitEnabled(boolean isEnabled);
    void setNeutralMode(MyNeutralMode mode);
    void setStatorCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime);
    void setSupplyCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime);
    void setOpenLoopRamp(double seconds);
    void getSuper();
}