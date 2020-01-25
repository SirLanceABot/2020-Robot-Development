package frc.components.powercellsupervisor.shuttle;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Shuttle
{
    private static final int MOTOR_ID = 1; //TOD0: Change to actual motor id
    private static final double TICKS_PER_ROTATION = 4096.0;

    private static CANSparkMax motor = new CANSparkMax(MOTOR_ID, MotorType.kBrushless);
    private static CANEncoder encoder = new CANEncoder(motor);
    private static CANPIDController pidController = new CANPIDController(motor);
    private static Shuttle instance = new Shuttle();
    private static double currentPosition;
    private static double targetPosition;

    protected Shuttle()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        motor.restoreFactoryDefaults();
        encoder.setPosition(0);
        currentPosition = 0;
        targetPosition = 0;
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    public static Shuttle getInstance()
    {
        return instance;
    }

    private void setSpeed(double speed)
    {
        motor.set(speed);
    }
    
    private void setSpeed()
    {
        motor.set(0.5);
    }

    private void stop()
    {
        motor.set(0.0);
    }
    
    private double getEncoderPosition()
    {
        return encoder.getPosition();
    }
    
    private void setEncoderPosition(double position)
    {
        encoder.setPosition(position);
    }

    private void setCurrentPosition(double position)
    {
        currentPosition = position;
    }

    public double getCurrentPosition()
    {
        return currentPosition;
    }

    public void setTargetPosition(double position)
    {
        targetPosition = position;
    }

    public double getTargetPosition()
    {
        return targetPosition;
    }

    private double inchesToTicks(double inches)
    {
        return inches * TICKS_PER_ROTATION;
    }

    public void moveOneBallLength()
    {
        setTargetPosition(currentPosition + inchesToTicks(7.0));
        if(currentPosition < targetPosition - 5 || currentPosition > targetPosition + 5)
        {
            setSpeed(0.5);
        }
        else
        {
            stop();
        }
    }

 
}