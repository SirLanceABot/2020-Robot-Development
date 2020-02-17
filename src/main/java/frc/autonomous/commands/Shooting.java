package frc.autonomous.commands;

import frc.autonomous.commands.interfaces.*;
import edu.wpi.first.wpilibj.Timer;
import frc.components.powercellsupervisor.intake.Intake;
import frc.components.powercellsupervisor.shooter.Shooter;

public class Shooting implements Command, Notifies
{
    private static boolean notification = false;
    private static Shooter shooter = Shooter.getInstance();
    private static double timeToWait;
    private static boolean isFinished;

    public Shooting()
    {

    }

    public void init()
    {
        System.out.println("Initializing Shooter");
        sendNotification(true);
    }

    public void execute()
    {   
        System.out.println("Executing Shooter");
        shooter.ShooterFSM();
        if(shooter.isOff())
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
        System.out.println("Ending Shooter");
        sendNotification(false);
    }


    @Override
    public void sendNotification(boolean notification) 
    {
        shooter.getNotification(notification);
    }
}