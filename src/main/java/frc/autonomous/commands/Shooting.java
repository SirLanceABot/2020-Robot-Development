package frc.autonomous.commands;

import frc.autonomous.commands.interfaces.*;
import frc.components.powercellsupervisor.shooter.Shooter;

public class Shooting implements Command, Notifies
{
    private boolean notification = false;
    private Shooter shooter = Shooter.getInstance();
    private boolean isFinished;

    public Shooting()
    {

    }

    public void init()
    {
        System.out.println("Initializing Shooter");
        sendNotification(true);
        shooter.runFSM();
    }

    public void execute()
    {   
        System.out.println("Executing Shooter");
        shooter.runFSM();
        if(shooter.isOff())
        {
            isFinished = true;
            sendNotification(false);
        }
    }

    public boolean isFinished()
    {
        shooter.runFSM();
        return isFinished;
    
    }

    public void end()
    {
        System.out.println("Ending Shooter");
        shooter.runFSM();
    }


    @Override
    public void sendNotification(boolean notification) 
    {
        shooter.getNotification(notification);
    }
}