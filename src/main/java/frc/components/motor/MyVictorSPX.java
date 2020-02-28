package frc.components.motor;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class MyVictorSPX implements Motor
{
    private WPI_VictorSPX motor;

    public MyVictorSPX(int port)
    {
        motor = new WPI_VictorSPX(port);
        motor.configFactoryDefault();
    }

    public void setInverted(boolean isInverted)
    {
        motor.setInverted(isInverted);
    }

    public void setReverseSoftLimitEnabled(boolean isEnabled)
    {
        motor.configReverseSoftLimitEnable(isEnabled);
    }

    public void setForwardSoftLimitEnabled(boolean isEnabled)
    {
        motor.configForwardSoftLimitEnable(isEnabled);
    }

    public void setNeutralMode(MyNeutralMode mode)
    {
        if (mode == MyNeutralMode.kBrake)
            motor.setNeutralMode(NeutralMode.Brake);
        else if(mode == MyNeutralMode.kCoast)
            motor.setNeutralMode(NeutralMode.Coast);
    }

    public void setStatorCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {
        
    }

    public void setSupplyCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {
        
    }

    public void setOpenLoopRamp(double seconds)
    {
        motor.configOpenloopRamp(seconds);
    }

    public void getSuper()
    {
        
    }
}