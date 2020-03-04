package frc.autonomous.commands;

import frc.autonomous.commands.interfaces.*;
import frc.components.drivetrain.Drivetrain;

/**
 * Auotonomous command to drive a certain relative distance
 * (only forward and backward)
 * @author Maxwell Li
 */
public class Drive implements Command
{
    //creation/init of instance variables
    private static Drivetrain drivetrain = Drivetrain.getInstance();
    private static boolean isFinished;
    private static double distance;

    //constuctor (takes the distance to drive)
    public Drive(double distanceToDrive)
    {
        distance = distanceToDrive;
    }

    //initialization function, first called when executing the command
    @Override
    public void init()
    {
        drivetrain.resetEncoders();
        System.out.println("Initializing Drive");
    }

    //execution function that is called when executing the command
    @Override
    public void execute()
    {   
        System.out.println("Executing Drive: " + distance);
        //drivetrain.drive(distance);
        //drivetrain.westCoastDrive(0.5, 0);
        if(drivetrain.drive(distance))
        {
            isFinished = true;
        }
    }

    //getter function for the isFinished flag
    @Override
    public boolean isFinished()
    {
        return isFinished;
    }

    //function to be called when the command ends
    @Override
    public void end()
    {
        System.out.println("Ending Drive");
    }
}