package frc.components.powercellsupervisor.shooter;

import frc.robot.Port;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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
    private static final double PROPORTIONAL = 0.5;
    private static final double INTEGRAL = 0.0000;
    private static final double DERIVATIVE = 0.00000;
    private static final double FEEDFORWARD = 0.00;
    private static final double TICK_TO_RPM = (1 / 100.0) * (1/4096.0) * (18/32.0) * (1000/1.0) * (60/1.0);
    //------------------------------------------------------------------//

    private static TalonSRX masterMotor = new TalonSRX(Port.Motor.CAN_SHOOTER_MASTER);
    private static VictorSPX followerMotor = new VictorSPX(Port.Motor.CAN_SHOOTER_SLAVE);
    private static boolean isMoving = false;
    private static Flywheel instance = new Flywheel();

    private Flywheel()
    {

        System.out.println(className + " : Constructor Started");
        masterMotor.configFactoryDefault();
        followerMotor.configFactoryDefault();
        masterMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT_MS);
        
        followerMotor.follow(masterMotor);

        masterMotor.config_kF(0, FEEDFORWARD, TIMEOUT_MS);
		masterMotor.config_kP(0, PROPORTIONAL, TIMEOUT_MS);
		masterMotor.config_kI(0, INTEGRAL, TIMEOUT_MS);
		masterMotor.config_kD(0, DERIVATIVE, TIMEOUT_MS);

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
        return masterMotor.getSelectedSensorVelocity() * TICK_TO_RPM;
    }

    /**
     * Method that should be called to run the flywheel
     * Uses a velocity PID Control for consistency when shooting
     * 
     * @param speedToRun in RPM
     */
    public void run(double speedToRun)
    {
        
        masterMotor.set(ControlMode.Velocity, speedToRun / TICK_TO_RPM);
        System.out.println(getRPM());
        //setSpeed(speedToRun);
    }

    public int getEncoderPosition()
    {
        return masterMotor.getSensorCollection().getQuadraturePosition();
    }

}
