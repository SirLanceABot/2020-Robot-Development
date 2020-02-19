package frc.robot;

import frc.vision.TargetDataB;
import frc.vision.TargetDataE;
import frc.vision.Vision;
import frc.controls.DriverController;
import frc.controls.Xbox;
import frc.controls.OperatorController;
import frc.controls.Logitech;
import frc.shuffleboard.MainShuffleboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * @author Elliot Measel
 * class for the test mode on the robot
 */
public class Test
{

    private static final String className = new String("[Test]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private static Vision vision = Vision.getInstance();

    private static TargetDataB turret = new TargetDataB();
    private static TargetDataE intake = new TargetDataE();

    private static DriverController driverController = DriverController.getInstance();
    private static OperatorController operatorController = OperatorController.getInstance();
    private static MainShuffleboard mainShuffleBoard = MainShuffleboard.getInstance();

    private static CANSparkMax leftMotor = new CANSparkMax(3, MotorType.kBrushless);
    private static CANSparkMax rightMotor = new CANSparkMax(2, MotorType.kBrushless);
    

    private static Test instance = new Test();

    /**
     * private constructor for test class
     */
    private Test()
    {
        System.out.println(className + " : Constructor Started");
        
        initializeDrivetrainMotors();

        System.out.println(className + ": Constructor Finished");
    }

    public static Test getInstance()
    {
        return instance;
    }

    public void init()
    {

    }

    public void periodic()
    {
        mainShuffleBoard.updateSensors();
        //testVisionUsingDrivetrain();
    }

    public void initializeDrivetrainMotors()
    {
        leftMotor.restoreFactoryDefaults();
        rightMotor.restoreFactoryDefaults();
        rightMotor.setInverted(true);
    }

    public void testVisionUsingDrivetrain()
    {
        if(!Vision.isConnected()) // For the actual robot, we will have an else block to switch to manual mode.
        {
            System.out.println("Waiting for a connection.");
            return;
        }

        intake = vision.getIntake();
        turret = vision.getTurret();
      
        if(turret.isFreshData())
        {
            if(turret.getAngleToTurn() > 1)
            {
                System.out.println("turning to the right" + "\t\tangle to turn: " + turret.getAngleToTurn());
                rightMotor.set(-0.1);
                leftMotor.set(0.1);
            }
            else if(turret.getAngleToTurn() < -1)
            {
                System.out.println("turning to the left" +  "\t\tangle to turn: " + turret.getAngleToTurn());
                rightMotor.set(0.1);
                leftMotor.set(-0.1);
            }
            else
            {
                System.out.print("it is off" + "\t\tangle to turn: " + turret.getAngleToTurn());
                rightMotor.set(0.0);
                leftMotor.set(0.0);
            }
        }
    }

    /**
     * Deals with the driver controls.
     */
    private void driverControls()
    {
        double move = driverController.getRawAxis(Xbox.Axis.kLeftY);
        double rotate = driverController.getRawAxis(Xbox.Axis.kRightX);
        double intake = driverController.getRawAxis(Xbox.Axis.kLeftTrigger);
        double wrist = driverController.getRawAxis(Xbox.Axis.kRightTrigger);

        //drivetrain.westCoastDrive(move, rotate);

        if(intake >= 0.5)
        {
            // Call up powercellsupervisor to turn on the roller
        }
        else if(wrist >= 0.5)
        {
            // Call up powercellsupervisor to toggle the wrist
        }
    }

    private void operatorControls()
    {
        double turret = operatorController.getRawAxis(Logitech.Axis.kZAxis);
        boolean shoot = operatorController.getRawButton(Logitech.Button.kTrigger);

        if(turret >= 0.5)
        {
            // Call up powercellsupervisor to turn the turret right
        }
        else if(turret <= -0.5)
        {
            // Call up powercellsupervisor to turn the turret left
        }
        else
        {
            // Call up powercellsupervisor to stop the turret
        }
        
        if(shoot)
        {
            // Call up powercellsupervisor to shoot the ball
        }
        else
        {
            // Call up powercellsupervisor to turn off the flywheel
        }
    }
}