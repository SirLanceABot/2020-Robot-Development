package frc.autonomous;

import frc.autonomous.commands.*;
import java.util.ArrayList;
import frc.autonomous.commands.interfaces.*;

/**
 * Class that will string the autonomous commands together
 * 
 */
public class AutonomousBuilder
{
    protected static ArrayList<ArrayList<Command>> masterCommandList = new ArrayList<>();


    private static AutonomousBuilder autoBuilder = new AutonomousBuilder();
    
    protected AutonomousBuilder()
    {
        //masterCommandList.add(new Wait(5.0));
    }

    /**
     * only adds one command to the node, aka a parrallel command
     * @param sequentialCommand
     */
    public static void addCommandNode(Command sequentialCommand)
    {
        ArrayList<Command> commandNode = new ArrayList<>();
        commandNode.add(sequentialCommand);
        masterCommandList.add(commandNode);
    }

    /**
     * adds a sequential command that is paired with a parrallel command to the command list
     * @param sequentialCOmmand
     */
    public static void addCommandNode(Command sequentialCommand, Command parrallelCommand)
    {
        ArrayList<Command> commandNode = new ArrayList<>();
        
        commandNode.add(sequentialCommand);
        commandNode.add(parrallelCommand);

        masterCommandList.add(commandNode);
    }

    

    public static AutonomousBuilder getInstance()
    {
        return autoBuilder;
    }

    public static ArrayList<ArrayList<Command>> getCommandList()
    {
        return masterCommandList;
    }

    public static void buildCommandList()
    {
        //insert code here to build the command list
    }

}