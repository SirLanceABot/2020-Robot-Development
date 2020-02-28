package frc.components.motor;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

public class MyTalonFX extends Motor
{
    private WPI_TalonFX motor;

    public MyTalonFX(int port)
    {
        motor = new WPI_TalonFX(port);
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
        motor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(isEnabled, currentLimit, triggerThresholdCurrent, triggerThresholdTime));
    }

    public void setSupplyCurrentLimit(boolean isEnabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(isEnabled, currentLimit, triggerThresholdCurrent, triggerThresholdTime));
    }

    public void setOpenLoopRamp(double seconds)
    {
        motor.configOpenloopRamp(seconds);
    }

    public void getSuper()
    {
        
    }
}