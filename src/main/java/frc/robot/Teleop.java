package frc.robot;

import java.sql.Driver;

import frc.components.climber.Climber;
import frc.components.drivetrain.Drivetrain;
import frc.components.powercellsupervisor.PowerCellSupervisor;
import frc.components.powercellsupervisor.shooter.Shooter;
import frc.components.powercellsupervisor.shuttle.Shuttle;
import frc.controls.DriverController;
import frc.controls.OperatorController;
import frc.controls.DriverController.AxisAction;
// import frc.components.climber.Climber;
// import frc.components.drivetrain.Drivetrain;
// import frc.components.powercellsupervisor.PowerCellSupervisor;
// import frc.controls.DriverController;
// import frc.controls.Xbox;
// import frc.controls.OperatorController;
// import frc.controls.Logitech;
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
        shuttle.runFSM();
        shooter.runFSM();
        drivetrain.westCoastDrive(driverController.getAction(AxisAction.kMove), driverController.getAction(AxisAction.kRotate));
        climber.run();
        System.out.println(driverController.getAction(AxisAction.kSpoolWinch));
        mainShuffleboard.updateSensors();
    }
}