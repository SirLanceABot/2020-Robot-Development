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
public class Flywheel extends PIDSubsystem
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
        super("Flywheel", PROPORTIONAL, INTEGRAL, DERIVATIVE, FEEDFORWARD); //(Name, P, I, D, FF)

        System.out.println(className + " : Constructor Started");

        masterMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT_MS); //TODO: Find out port
        followerMotor.follow(masterMotor);
        setAbsoluteTolerance(0.05);
        getPIDController();

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
     * init command needed for the PID control
     */
    public void initDefaultCommand()
    {

    }

    /**
     * @return returns the value that the PID controller should use
     */
    protected double returnPIDInput()
    {
        // return masterMotor.getMotorOutputVoltage();
        return masterMotor.getSelectedSensorVelocity(0); // returns the sensor value that is providing the feedback for the system
    }

    /**
    * uses the PID to control the motor
    */
    protected void usePIDOutput(double output)
    {
        setSpeed(output);// this is where the computed output value fromthe PIDController is applied to the motor
        setIsMoving(true);
    }

    /**
     * sets the speed of the motor
     * @param speed
     */
    private void setSpeed(double speed)
    {
        masterMotor.set(ControlMode.PercentOutput, speed);
        setIsMoving(true);
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

    /**
     * Method that should be called to run the flywheel
     * Uses a velocity PID Control for consistency when shooting
     * @param speedToRun
     */
    public void run(double speedToRun)
    {
        usePIDOutput(speedToRun);
    }

    public int getEncoderPosition()
    {
        return masterMotor.getSelectedSensorPosition();
    }

}
