package frc.components.motor;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

public class MyTalonSRX extends Motor
{
    private WPI_TalonSRX motor;

    public MyTalonSRX(int port)
    {
        motor = new WPI_TalonSRX(port);
        motor.configFactoryDefault();
    }

    @Override
    public void setInverted(boolean isInverted)
    {
        motor.setInverted(isInverted);
    }

    public void setReverseSoftLimitEnabled(boolean isEnabled)
    {
        motor.configReverseSoftLimitEnable(isEnabled);
    }

    public void setReverseSoftLimitThreshold(int threshold)
    {
        motor.configReverseSoftLimitThreshold(threshold);
    }

    public void setReverseHardLimitEnabled(boolean isEnabled, boolean isNormallyOpen)
    {
        
    }

    public void setForwardSoftLimitEnabled(boolean isEnabled)
    {
        motor.configForwardSoftLimitEnable(isEnabled);
    }

    public void setForwardSoftLimitThreshold(int threshold)
    {
        motor.configForwardSoftLimitThreshold(threshold);
    }

    public void setForwardHardLimitEnabled(boolean isEnabled, boolean isNormallyOpen)
    {
       
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
        //WPI_TalonSRX has no configStatorCurrentLimit()
    }

    public void setSupplyCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(isEnabled, currentLimit, triggerThresholdCurrent, triggerThresholdTime));
    }

    public void setOpenLoopRamp(double seconds)
    {
        motor.configOpenloopRamp(seconds);
    }

    public WPI_TalonSRX getSuper()
    {
        return motor;
    }
}