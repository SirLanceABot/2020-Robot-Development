package frc.components.drivetrain;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Drivetrain extends DifferentialDrive
{
    private static WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(1);
    private static WPI_TalonSRX backRightMotor = new WPI_TalonSRX(2);
    private static WPI_TalonSRX backLeftMotor = new WPI_TalonSRX(3);
    private static WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(4);
    
    private static SpeedControllerGroup rightMotors = new SpeedControllerGroup(frontRightMotor, backRightMotor);
    private static SpeedControllerGroup leftMotors = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);

    private static Drivetrain instance = new Drivetrain();

    private Drivetrain()
    {
        super(leftMotors, rightMotors);
        frontRightMotor.configFactoryDefault();
        frontLeftMotor.configFactoryDefault();
        backRightMotor.configFactoryDefault();
        backLeftMotor.configFactoryDefault();

        //These invert the motor from the firmware on the motor controller
        frontRightMotor.setInverted(true);
        frontLeftMotor.setInverted(false);
        backRightMotor.setInverted(true);
        backLeftMotor.setInverted(false);

        /**
         * This one inverts within software (flips a boolean)
         * // motorsRight.setInverted(false);
         */

         //By default the differential drive is inverted so this sets it so that it is not inverted
        setRightSideInverted(false);
    }

    public static Drivetrain getInstance()
    {
        return instance;
    }

    public void westcoastDrive(double move, double rotate)
    {
        super.arcadeDrive(move, rotate);
    }

    public void westcoastDrive(double move, double rotate, Boolean squared)
    {
        super.arcadeDrive(move, rotate, squared);
    }
}