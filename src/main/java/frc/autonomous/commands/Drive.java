package frc.autonomous.commands;

import frc.autonomous.commands.interfaces.*;
import frc.components.drivetrain.Drivetrain;

public class Drive implements Command
{
    private static Drivetrain drivetrain = Drivetrain.getInstance();
    private static boolean isFinished;
    private static double distance;

    public Drive(double distanceToDrive)
    {
        distance = distanceToDrive;
    }

    public void init()
    {
        System.out.println("Initializing Drive");
    }

    public void execute()
    {   
        System.out.println("Executing Drive");
        drivetrain.drive(distance);
        if(drivetrain.drive(distance))
        {
            isFinished = true;
        }
    }

    public boolean isFinished()
    {
        return isFinished;
    }

    public void end()
    {
        System.out.println("Ending Drive");
    }
}