package frc.autonomous.commands;

import javax.lang.model.util.ElementScanner6;

import frc.autonomous.commands.interfaces.*;
import frc.components.drivetrain.Drivetrain;
import frc.components.powercellsupervisor.shooter.Shooter;

public class Rotate implements Command
{
    private static Drivetrain drivetrain = Drivetrain.getInstance();
    private static double absoluteAngle;
    private static boolean isFinished;

    public Rotate(double angle)
    {
        absoluteAngle = angle;
    }

    public void init()
    {
        System.out.println("Initializing Rotation");
    }

    public void execute()
    {   
        System.out.println("Executing Rotation");
        if(drivetrain.rotateTo(absoluteAngle))
        {
            isFinished = true;
        }
        else
        {
            isFinished = false;
        }
        // if(drivetrain.stop());
        // {
        //     isFinished = true;
        // }
    }

    public boolean isFinished()
    {
        return isFinished;
    }

    public void end()
    {
        System.out.println("Ending Rotation");
    }
}