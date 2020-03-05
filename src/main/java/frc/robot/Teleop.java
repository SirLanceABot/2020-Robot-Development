package frc.robot;

import frc.components.climber.Climber;
import frc.components.drivetrain.Drivetrain;
import frc.components.powercellsupervisor.PowerCellSupervisor;
import frc.components.powercellsupervisor.intake.Intake;
import frc.components.powercellsupervisor.shooter.Flywheel;
import frc.components.powercellsupervisor.shooter.Shooter;
import frc.components.powercellsupervisor.shooter.Shroud;
import frc.components.powercellsupervisor.shooter.Turret;
import frc.components.powercellsupervisor.shuttle.Shuttle;

import frc.controls.DriverController;
// import frc.controls.DriverController.AxisAction;
// import frc.controls.DriverController.ButtonAction;

import frc.controls.OperatorController;
// import frc.controls.OperatorController.AxisAction;
// import frc.controls.OperatorController.ButtonAction;


import frc.shuffleboard.MainShuffleboard;

/**
 * Class for controlling the robot during the Teleop period.
 * @author Darren Fife
 */
public class Teleop
{
    private static final String className = new String("[Teleop]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    // private static Vision vision = Vision.getInstance();
    // private static Climber climber = Climber.getInstance();
    // private static Drivetrain drivetrain = Drivetrain.getInstance();
    // private static PowerCellSupervisor powercellsupervisor = PowerCellSupervisor.getInstance();
    
    // private static DriverController driverController = DriverController.getInstance();
    // private static OperatorController operatorController = OperatorController.getInstance();
    private static MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();

    private static Shuttle shuttle = Shuttle.getInstance();
    private static Shooter shooter = Shooter.getInstance();
    private static Drivetrain drivetrain = Drivetrain.getInstance();
    private static Climber climber = Climber.getInstance();
    private static OperatorController operatorController = OperatorController.getInstance();
    private static DriverController driverController = DriverController.getInstance();
    private static PowerCellSupervisor powerCellSupervisor = PowerCellSupervisor.getInstance();
    private static Flywheel flywheel = Flywheel.getInstance();
    private static Turret turret = Turret.getInstance();
    private static Shroud shroud = Shroud.getInstance();
    private static Intake intake = Intake.getInstance();


    private static Teleop teleop = new Teleop();

    private Teleop()
    {
        System.out.println(className + " : Constructor Started");

        System.out.println(className + ": Constructor Finished");
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
        //running the shuttle with an override capability
        if(operatorController.getAction(frc.controls.OperatorController.OperatorButtonAction.kShuttleOverride))
        {
            shuttle.overrideSetSpeet(0.25);
        }
        else
        {
            shuttle.runFSM();
        }
   
        //running the shooter
        if(operatorController.getAction(frc.controls.OperatorController.OperatorButtonAction.kShooterOverride))
        {
            turret.setSpeed(operatorController.getAction(frc.controls.OperatorController.OperatorAxisAction.kTurret)); 
            flywheel.setSpeedOverride(operatorController.getAction(frc.controls.OperatorController.OperatorAxisAction.kShooterPower));
            shroud.setSpeed(operatorController.getAction(frc.controls.OperatorController.OperatorAxisAction.kShroud));
        }
        else
        {
            shooter.runFSM();
        }

        //running the intake
        intake.runFSM();

        //run the drivetrain
        drivetrain.westCoastDrive(driverController.getAction(frc.controls.DriverController.DriverAxisAction.kMove)
                                , driverController.getAction(frc.controls.DriverController.DriverAxisAction.kRotate));

        //run the climber
        climber.run();
        mainShuffleboard.updateSensors();
    }
}