/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.controls.DriverController;
import frc.controls.OperatorController;
import frc.shuffleboard.MainShuffleboard;
import frc.vision.Vision;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot
{
    public enum RobotState
    {
        kNone,
        kStartup,
        kDisabledBeforeGame,
        kAutonomous,
        kDisabledBetweenAutonomousAndTeleop,
        kTeleop,
        kDisabledAfterGame,
        kTest;
    }

    private static Vision vision = Vision.getInstance();
    private static RobotState robotState = RobotState.kNone;
    private static Test test = Test.getInstance();
    private static Autonomous autonomous = Autonomous.getInstance();
    private static Disabled disabled = Disabled.getInstance();
    private static Teleop teleop = Teleop.getInstance();

    private MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();
    
    public Robot()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        robotState = RobotState.kStartup;
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    @Override
    public void robotInit()
    {
        System.out.println("2020-Robot-Development");
    }

    /**
     * This function is called periodically.
     */
    @Override
    public void robotPeriodic()
    {
        mainShuffleboard.updateMatchTime();
    }

    /**
     * This function is run once each time the robot enters autonomous mode.
     */
    @Override
    public void autonomousInit()
    {
        robotState = RobotState.kAutonomous;

        autonomous.init();
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic()
    {
        autonomous.periodic();
    }

    /**
     * This function is called once each time the robot enters teleoperated mode.
     */
    @Override
    public void teleopInit()
    {
        robotState = RobotState.kTeleop;

        teleop.init();
    }

    /**
     * This function is called periodically during teleoperated mode.
     */
    @Override
    public void teleopPeriodic()
    {
        teleop.periodic();
    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    @Override
    public void testInit()
    {
        robotState = RobotState.kTest;

        test.init();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic()
    {
        test.periodic();
    }

        /**
     * This function is called once each time the robot is disabled.
     */
    @Override
    public void disabledInit()
    {
        if (robotState == RobotState.kStartup)
            robotState = RobotState.kDisabledBeforeGame;
        else if (robotState == RobotState.kAutonomous)
            robotState = RobotState.kDisabledBetweenAutonomousAndTeleop;
        else if (robotState == RobotState.kTeleop)
            robotState = RobotState.kDisabledAfterGame;

        disabled.init();
    }

    /**
     * This function is called periodically when the robot is disabled.
     */
    @Override
    public void disabledPeriodic()
    {
        disabled.periodic();
    }

    public static RobotState getRobotState()
    {
        return robotState;
    }
}