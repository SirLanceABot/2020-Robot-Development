package frc.autonomous.commands;

import edu.wpi.first.wpilibj.Timer;

public class Wait implements Command
{
    private static Timer timer = new Timer();
    private static double timeToWait;
    private static boolean isFinished;
    public Wait(double time)
    {
        timeToWait = time;
        isFinished = false;
    }

    public void init()
    {
        timer.stop();
        timer.reset();
        timer.start();
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
    }

}