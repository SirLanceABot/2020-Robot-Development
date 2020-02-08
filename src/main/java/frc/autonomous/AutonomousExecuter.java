package frc.autonomous;

import java.util.ArrayList;

import frc.autonomous.commands.Command;

public class AutonomousExecuter
{
    private static boolean hasBeenInitialized = false;
    private static int currentCommandIndex = 0;
    private static AutonomousExecuter autoExecuter = new AutonomousExecuter();
    private static AutonomousBuilder autoBuilder = AutonomousBuilder.getInstance();

    private static ArrayList<Command> commandList = autoBuilder.getCommandList();

    private AutonomousExecuter()
    {

    }

    public static AutonomousExecuter getAutonomousExecuter()
    {
        return autoExecuter;
    }

    public void executeAuto()
    {
        Command currentCommand = commandList.get(currentCommandIndex);
        
        if(currentCommand.isFinished() == true)
        {
            currentCommandIndex++;
            hasBeenInitialized = false;
        }
        else if(hasBeenInitialized == false)
        {
            currentCommand.init();
            hasBeenInitialized = true;
        }   
        else
        {
            currentCommand.execute();
        }
        
    }


}