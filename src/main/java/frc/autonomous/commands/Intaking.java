package frc.autonomous.commands;

import frc.autonomous.commands.interfaces.*;
import edu.wpi.first.wpilibj.Timer;
import frc.components.powercellsupervisor.intake.Intake;

public class Intaking implements Command, Notifies
{
    private static boolean notification = false;
    private static Intake intake = Intake.getInstance();

    private static Timer timer = new Timer();
    private static double timeToWait;
    private static boolean isFinished;

    public Intaking(double time)
    {
        timeToWait = time;
        isFinished = false;
    }

    public void init()
    {
        timer.stop();
        timer.reset();
        timer.start();
        sendNotification(true);
    }

    public void execute()
    {
        
        if(timer.get() > timeToWait)
        {
            end();
        }
    }

    public boolean isFinished()
    {
        return isFinished;
    }

    public void end()
    {
        timer.stop();
        timer.reset();
        isFinished = true;
        sendNotification(false);

    }


    @Override
    public void sendNotification(boolean notification) 
    {
        intake.getNotification(notification);
    }
}