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

    //TODO: determine actual height constraints.
    private static final double MINIMUM_HEIGHT = 0.0;
    private static final double MAXIMUM_HEIGHT = 50.0;
    private static final int ERROR_THRESHOLD = 5; // TODO: find actual threshold we want for the robot

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
    public static Winch getInstance()
    {
        return instance;
    }

    /**
     * Returns the encoder value of winchMotor.
     * @return winchEncoder.getPosition()   
     * The location of the encoder (ticks?).
     */
    public double getEncoderPosition()
    {
        return winchEncoder.getPosition();
    }

    /**
     * Sets the speed of winchMotor.
     * @param speed The speed at which the winch spools the rope. Values are from -1 to 1.
     */
    private void setWinchSpeed(double speed)
    {
        winchMotor.set(speed);
    }
    
    //--------------------------------------------------------//
    //             TODO: HOW FAST TO REEL IN ROPE
    //--------------------------------------------------------//

    //constant for how fast the winch reels in rope
    private final double WINCH_WIND_SPEED = 0.5;

    public void windWinch()
    {
        setWinchSpeed(WINCH_WIND_SPEED);
    }

    /**
     * The method to stop the winch.
     */
    public void stopWinch()
    {
        setWinchSpeed(0.0);
    }

    //--------------------------------------------------------//
    //             TODO: getDistance() method to 
    //             calculate distance of rope reeled
    //             given encoder value (need gear ratios)
    //--------------------------------------------------------//

    /**
     * The method to lower the Winch.
     */
    public void lowerWinch(double speed)
    {
        double currentPosition = getEncoderPosition();
        if(currentPosition > MINIMUM_HEIGHT - ERROR_THRESHOLD)
        {
            setWinchSpeed(-Math.abs(speed));
        }
        else
        {
            System.out.println("Cannot lower the winch. It is already at the minimum height.");
        }
    }

    /**
     * The method to raise the Winch.
     */
    public void raiseWinch(double speed)
    {
        double currentPosition = getEncoderPosition();
        if(currentPosition < MAXIMUM_HEIGHT + ERROR_THRESHOLD)
        {
            setWinchSpeed(Math.abs(speed));
        }
        else
        {
            System.out.println("Cannot raise the winch. It is already at the maximum height.");
        }
    }
}