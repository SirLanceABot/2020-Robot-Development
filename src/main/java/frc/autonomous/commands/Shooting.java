package frc.autonomous.commands;

import frc.autonomous.commands.interfaces.*;
import edu.wpi.first.wpilibj.Timer;
import frc.components.powercellsupervisor.intake.Intake;
import frc.components.powercellsupervisor.shooter.Shooter;

public class Shooting implements Command, Notifies
{
    private static boolean notification = false;
    //private static Shooter shooter = Shooter.getInstance();
    private static double timeToWait;
    private static boolean isFinished;

    public Shooting()
    {

    }

    public void init()
    {
    }

    public void execute()
    {
        
    }

    public boolean isFinished()
    {
        return isFinished;
    }

    public void end()
    {
    }


    @Override
    public void sendNotification(boolean notification) 
    {
    }
}