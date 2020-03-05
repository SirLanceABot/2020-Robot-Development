package frc.components.powercellsupervisor.shooter;

import frc.robot.Port;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.sensors.NavX;
import frc.vision.TargetDataB;
import frc.vision.Vision;


/**
 * Class for controlling the turret that aims the shooter on the X plane
 * 0 degrees is straight ahead, -180 to the left, +180 to the right
 * @author Maxwell Li
 */
public class Turret
{
    private static final String className = new String("[Turret]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    public static class Gains {
        public final double kP;
        public final double kI;
        public final double kD;
        public final double kF;
        public final int kIzone;
        public final double kPeakOutput;
        
        public Gains(double _kP, double _kI, double _kD, double _kF, int _kIzone, double _kPeakOutput){
            kP = _kP;
            kI = _kI;
            kD = _kD;
            kF = _kF;
            kIzone = _kIzone;
            kPeakOutput = _kPeakOutput;
        }
    }
    	/**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
	public static final int kSlotIdx = 0;

	/**
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;

	/**
	 * set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
	public static final int kTimeoutMs = 30;

	/**
	 * Gains used in Motion Magic, to be adjusted accordingly
     * Gains(kp, ki, kd, kf, izone, peak output);
     */
    static final Gains kGains = new Gains(0.2, 0.0, 0.0, 0.0, 0, 1.0);

    private static final double kZERO_LOCATION = 0; //TODO: Find out what value is straight ahead
    private static final int kTIMEOUT_MS = 30;
    private static final double kTOTAL_TICKS = 0.0; //TODO: Find out how many total ticks there are


    //Vision stuff
    private static Vision vision = Vision.getInstance();
    private static TargetDataB turretVision = new TargetDataB();
    private static NavX navX = NavX.getInstance();
    private static TalonSRX motor = new TalonSRX(Port.Motor.CAN_TURRET);
    private static double currentPostion;
    private static boolean isMoving = false;
    private static Turret instance = new Turret();

    private Turret()
    {
        System.out.println(className + " : Constructor Started");

        motor.configFactoryDefault();
        motor.setInverted(false);
        motor.setNeutralMode(NeutralMode.Brake);

        //feedback sensor
        motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        motor.setSensorPhase(false);
        // motor.configClearPositionOnLimitR(true, 10);
        // motor.configFeedbackNotContinuous(false, 10);

        //soft limits
        // motor.configReverseSoftLimitThreshold(1);
        // motor.configReverseSoftLimitEnable(true);
        // motor.configForwardSoftLimitThreshold(359); // TODO: put real absolute encoder values in
        // motor.configForwardSoftLimitEnable(true);

        // //hard limits
        // motor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
        // motor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
        
        //current limits
        //motor.configOpenloopRamp(0.1);
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, 0.5), 10);

        /* Set the peak and nominal outputs */
		// motor.configNominalOutputForward(0, kTimeoutMs);
		// motor.configNominalOutputReverse(0, kTimeoutMs);
		// motor.configPeakOutputForward(1, kTimeoutMs);
		// motor.configPeakOutputReverse(-1, kTimeoutMs);

		// /* Set Motion Magic gains in slot0 - see documentation */
		// motor.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
		// motor.config_kF(kSlotIdx, kGains.kF, kTimeoutMs);
		// motor.config_kP(kSlotIdx, kGains.kP, kTimeoutMs);
		// motor.config_kI(kSlotIdx, kGains.kI, kTimeoutMs);
		// motor.config_kD(kSlotIdx, kGains.kD, kTimeoutMs); 
        
        System.out.println(className + ": Constructor Finished");
    }

    public static Turret getInstance()
    {
        return instance;
    }

    /**
     * Private function that handles all of the setting of motor speed
     * @param speed
     */
    public void setSpeed(double speed)
    {
        motor.set(ControlMode.PercentOutput, speed);
        setCurrentPosition(getEncoderPosition());
        setIsMoving(true);
    }

    /**
     * Stops the motors
     */
    public void stop()
    {
        motor.set(ControlMode.PercentOutput, 0.0);
        setCurrentPosition(getEncoderPosition());
        setIsMoving(false);
    }

    /**
     * sets the current position of the turret (ticks)
     * @param position
     */
    private void setCurrentPosition(double position)
    {
        currentPostion = position;
    }

    /**
     * Setter method for the encoder position (in encoder units)
     * @param position
     */
    private void setEncoderPosition(int position)
    {
		motor.setSelectedSensorPosition(position, 0, kTIMEOUT_MS);
    }

    /**
     * Getter method to get the position in encoder untis
     * @return
     */
    public double getEncoderPosition()
    {
        return motor.getSelectedSensorPosition(0);
    }

    /**
     * gets the current position (absolute degrees)
     * @return
     */
    public double getCurrentPosition()
    {
        return currentPostion;
    }

    /**
     * Uses the current position in encoder values to calc the angle (absolute)
     * @return the absolute angle of the turret
     */
    public double getCurrentAngle()
    {
        return ((currentPostion - kZERO_LOCATION) / kTOTAL_TICKS) * 180.0;
    }

	/**
	 * @param units CTRE mag encoder sensor units 
	 * @return degrees rounded to tenths.
	 */
	String ToDeg(int units) {
		double deg = units * 360.0 / 4096.0;

		/* truncate to 0.1 res */
		deg *= 10;
		deg = (int) deg;
		deg /= 10;

		return "" + deg;
	}

    /**
     * sets the instance variable that determines if it is running
     * @param moving
     */
    private static void setIsMoving(boolean moving)
    {
        isMoving = moving;
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
     * Init function to be run to zero the location of the turret out.
     * Can be run at the init for auto and then again for teleop so the 
     * turret always starts in the same position 
     */
    public void init()
    {   
        double currentPositionInTicks = getEncoderPosition();

        if(currentPositionInTicks > kZERO_LOCATION + 5)
        {
            setSpeed(-0.1);
        }
        else if(currentPositionInTicks < kZERO_LOCATION - 5)
        {
            setSpeed(0.1);
        }
        else
        {
            setSpeed(0.0);
            setEncoderPosition(0); //TODO: Testing
        }
    }

    /**
     * Moves the shooter to an absolute position (in degrees)
     * TODO: needs to use a PID loop
     * @param angle
     */
    public void rotateTo(double angle)
    {
        double currentAngle = getCurrentAngle();
        if(angle < currentAngle - 5)
        {
            setSpeed(0.5);
        }
        else if(angle > currentAngle + 5)
        {
            setSpeed(-0.5);
        }
        else
        {
            stop();
        }
    }
    

    /**
     * Rotates the turret to a relative position (in degrees)
     * Uses a PID Loop
     * @param angle
     */
    public void rotate(double angle)
    {
        rotateTo(angle + getCurrentAngle());
    }

    public void rotateToWall()
    {
        double targetAngle = navX.getAngleOfPowerPortWall();
        double currentAngle = navX.getAngleOfPowerPortWall();

        rotateTo(targetAngle - currentAngle);
    }

    /**
     * @return true when aligned, false when not aligned
     */
    public boolean alignWithTarget()
    {
        turretVision = vision.getTurret();

        if(turretVision.isFreshData())
        {
            if(turretVision.getAngleToTurn() > 0.25)
            {
                System.out.println("turning to the right" + "\t\tangle to turn: " + turretVision.getAngleToTurn());
                setSpeed(0.09);
                return false;
            }
            else if(turretVision.getAngleToTurn() < -0.25)
            {
                System.out.println("turning to the left" +  "\t\tangle to turn: " + turretVision.getAngleToTurn());
                setSpeed(-0.09);
                return false;
            }
            else
            {
                System.out.print("it is off" + "\t\tangle to turn: " + turretVision.getAngleToTurn());
                stop();
                return true;
            }
        }
        return false;

    }

    public String toString()
    {
        int selSenPos = motor.getSelectedSensorPosition(0);
		int pulseWidthWithoutOverflows = motor.getSensorCollection().getPulseWidthPosition() & 0xFFF;
                
        /**
		 * Display how we've adjusted PWM to produce a QUAD signal that is
		 * absolute and continuous. Show in sensor units and in rotation
		 * degrees.
		 */
		System.out.print("pulseWidPos:" + pulseWidthWithoutOverflows + "   =>    " + "selSenPos:" + selSenPos);
        System.out.print("      ");
        System.out.print("pulseWidDeg:" + ToDeg(pulseWidthWithoutOverflows) + "   =>    " + "selSenDeg:" + ToDeg(selSenPos));
        return "\n";

    }

    public String getTurretData()
    {
        return String.format("%+5.2f %6f %4.1f %4.1f", 
            motor.getMotorOutputPercent(), motor.getSelectedSensorPosition(), 
            motor.getStatorCurrent(), motor.getTemperature());
    }
}