package frc.autonomous.commands;


import frc.autonomous.commands.interfaces.*;
import frc.components.drivetrain.Drivetrain;

public class DriveTo implements Command
{
    private static Drivetrain drivetrain = Drivetrain.getInstance();
    private static double absoluteAngle;
    private static boolean isFinished;
    private static double xPos = 0.0;
    private static double yPos = 0.0;

    public DriveTo(double xPosition, double yPosition)
    {
        xPos = xPosition;
        yPos = yPosition;
    }

    public void init()
    {
        System.out.println("Initializing Drive To:" + xPos + "," + yPos);
    }

    public void execute()
    {   
        System.out.println("Executing Drive To: " + xPos + "," + yPos);
        //Need to add the math for an absolute coordiante system
        //will combine the drive and the rotate class,
        //merely acts as a wrapper for the two and coordinates them
    }


    public boolean isFinished()
    {
        return isFinished;
    }

    public void end()
    {
        System.out.println("Ending Drive" + xPos + "," + yPos);
    }
}