package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//import frc.components.climber.Climber;
import frc.components.drivetrain.Drivetrain;
// import frc.components.powercellsupervisor.PowerCellSupervisor;
import frc.controls.DriverController;
import frc.controls.Logitech;
import frc.controls.OperatorController;
import frc.controls.Xbox;
import frc.shuffleboard.MainShuffleboard;
import frc.vision.TargetDataB;
import frc.vision.TargetDataE;
import frc.vision.UdpReceive;
import frc.vision.Vision;

/**
 * Class for controlling the robot during the Teleop period.
 * @author Darren Fife
 */
public class Teleop
{
    //private static Climber climber = Climber.getInstance();
    //private static Drivetrain drivetrain = Drivetrain.getInstance();
    // private static PowerCellSupervisor powercellsupervisor = PowerCellSupervisor.getInstance();
    private static Vision vision = Vision.getInstance();
    private static DriverController driverController = DriverController.getInstance();
    private static OperatorController operatorController = OperatorController.getInstance();
    private static MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();

    private static TargetDataB turret = new TargetDataB();
    private static TargetDataE intake = new TargetDataE();

    private static CANSparkMax leftMotor = new CANSparkMax(3, MotorType.kBrushless);
    private static CANSparkMax rightMotor = new CANSparkMax(2, MotorType.kBrushless);

    private static Teleop teleop = new Teleop();

    private Teleop()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        leftMotor.restoreFactoryDefaults();
        rightMotor.restoreFactoryDefaults();
        rightMotor.setInverted(true);
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    public static Teleop getInstance()
    {
        return teleop;
    } 

    /**
     * Contains all the intializations needed before teleop.
     */
    public void init()
    {
        mainShuffleboard.setDriverControllerSettings();
        mainShuffleboard.setOperatorControllerSettings();
    }

    /**
     * Contains the statements needed to be called periodically during teleop.
     */
    public void periodic()
    {
        if(!Vision.isConnected()) // For the actual robot, we will have an else block to switch to manual mode.
        {
            System.out.println("Waiting for a connection.");
            return;
        }
        driverController.checkRumbleEvent();

        // System.out.println(driverController.getAction(DriverController.AxisAction.kMove));
        // System.out.println(operatorController.getRawAxis(OperatorController.Axis.kXAxis));

        // driverController.getRawButton(Xbox.Button.kStart);

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