package frc.components.climber;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

/**
 * Reels in rope to lift the robot.
 * @author Joey Pietrogallo 1/15/20
 */
public class Winch
{
    private static CANSparkMax winchMotor = new CANSparkMax(1, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    private static CANEncoder winchEncoder = winchMotor.getEncoder();
    private static Winch instance = new Winch();
    
    /**
     * The constructor for the Winch.
     */
    private Winch()
    {
        winchMotor.restoreFactoryDefaults();
    }

    /**
     * Returns an instance of the Winch.
     * @return instance
     */
    public Winch getInstance()
    {
        return instance;
    }

    /**
     * Sets the speed of winchMotor.
     * @param speed The speed at which the winch spools the rope. Values are from -1 to 1.
     */
    private void setWinchSpeed(double speed)
    {
        winchMotor.set(speed);
    }

    //constant for how fast the winch reels in rope
    //TO-DO: HOW FAST TO REEL IN ROPE
    private final double WINCH_WIND_SPEED = 0.5;

    public void windWinch()
    {
        setWinchSpeed(WINCH_WIND_SPEED);
    }

    /**
     * Stops the winch.
     */
    public void stopWinch()
    {
        setWinchSpeed(0.0);
    }
}