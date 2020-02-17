package frc.robot;

import frc.robot.Robot.RobotState;
import frc.shuffleboard.MainShuffleboard;

/**
 * @author Elliot Measel class for the disabled mode on the robot
 */
public class Disabled
{
    private RobotState robotState;

    private static MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();

    private static Disabled instance = new Disabled();
    /**
     * private constructor for disabled class
     */
    private Disabled()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    public static Disabled getInstance()
    {
        return instance;
    }

    public void init()
    {
        robotState = Robot.getRobotState();
    }

    public void periodic()
    {
        if (robotState == RobotState.kDisabledBeforeGame)
        {
            mainShuffleboard.updateMatchInfo();
            mainShuffleboard.checkForNewAutonomousTabData();
        }
    }
}