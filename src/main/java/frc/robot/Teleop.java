package frc.robot;

import frc.components.climber.Climber;
import frc.components.drivetrain.Drivetrain;
// import frc.components.powercellsupervisor.PowerCellSupervisor;
import frc.controls.DriverController;
import frc.controls.Logitech;
import frc.controls.OperatorController;
import frc.controls.Xbox;

/**
 * Class for controlling the robot during the Teleop period.
 * @author Darren Fife
 */
public class Teleop
{
    // private static Climber climber = Climber.getInstance();
    private static Drivetrain drivetrain = Drivetrain.getInstance();
    // private static PowerCellSupervisor powercellsupervisor = PowerCellSupervisor.getInstance();
    private static DriverController driverController = DriverController.getInstance();
    private static OperatorController operatorController = OperatorController.getInstance();

    public Teleop()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    /**
     * Contains all the intializations needed before teleop.
     */
    public void init()
    {

    }

    /**
     * Contains the statements needed to be called periodically during teleop.
     */
    public void periodic()
    {
        driverControls();
        operatorControls();
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

        drivetrain.westCoastDrive(move, rotate);

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
        
        if(shoot)
        {
            // Call up powercellsupervisor to shoot the ball
        }
    }
}