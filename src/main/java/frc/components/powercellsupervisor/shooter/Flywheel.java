package frc.components.powercellsupervisor.shooter;

import frc.robot.Port;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

/**
 * Class for the flywheel that ejects the power cells from the shooter
 * @author Maxwell Lee
 */
public class Flywheel
{
    private static final String className = new String("[Flywheel]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    //----------------------------- Constants --------------------------//
    private static final int TIMEOUT_MS = 30;
    //private static final int VELOCITY_ERROR = 3;
    private static final double PROPORTIONAL = 0.0;
    private static final double INTEGRAL = 0.0;
    private static final double DERIVATIVE = 0.0;
    private static final double FEEDFORWARD = 0.0;
    //------------------------------------------------------------------//

    private static TalonSRX masterMotor = new TalonSRX(Port.Motor.SHOOTER_MASTER);
    private static VictorSPX followerMotor = new VictorSPX(Port.Motor.SHOOTER_SLAVE);
    private static boolean isMoving = false;
    private static Flywheel instance = new Flywheel();

    private Flywheel()
    {

        System.out.println(className + " : Constructor Started");

        masterMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT_MS); //TODO: Find out port
        followerMotor.follow(masterMotor);

        System.out.println(className + ": Constructor Finished");
    }

    /**
     * Get the instance of the flywheel to be used
     * @return the flywheel instance
     */
    public static Flywheel getInstance()
    {
        return instance;
    }



    /**
     * sets the speed of the motor
     * @param speed
     */
    private void setSpeed(double speed)
    {
        if(speed == 0.0)
        {
            stop();
        }
        else
        {
            masterMotor.set(ControlMode.PercentOutput, speed);
            setIsMoving(true);
        }

    }

    /**
     * Stops the motor
     */
    public void stop()
    {
        masterMotor.set(ControlMode.PercentOutput, 0.0);
        setIsMoving(false);
    }

    /**
     * sets the instance variable that determines if it is running
     * @param running
     */
    private static void setIsMoving(boolean running)
    {
        isMoving = running;
    }

    /**
     * getter function for the isRunning boolean
     * @return true if it is running, false if it is not
     */
    public boolean getIsMoving()
    {
        return isMoving;
    }

    public double getRPM()
    {
        return 0.0; //need to do calculation
    }

    /**
     * Method that should be called to run the flywheel
     * Uses a velocity PID Control for consistency when shooting
     * 
     * @param speedToRun in RPM
     */
    public void run(double speedToRun)
    {
        setSpeed(speedToRun);
    }

    public int getEncoderPosition()
    {
        return masterMotor.getSelectedSensorPosition();
    }

}
