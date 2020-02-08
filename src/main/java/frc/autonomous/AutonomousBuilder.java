package frc.autonomous;

import frc.autonomous.commands.*;
import java.util.ArrayList;

/**
 * Class that will string the autonomous commands together
 * 
 */
public class AutonomousBuilder
{
    protected static ArrayList<Command> commandList = new ArrayList<>();
    
    private static AutonomousBuilder autoBuilder = new AutonomousBuilder();
    
    protected AutonomousBuilder()
    {
        commandList.add(new Wait(5.0));
    }

    public static AutonomousBuilder getInstance()
    {
        return autoBuilder;
    }

    public static ArrayList<Command> getCommandList()
    {
        return commandList;
    }


}