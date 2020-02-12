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

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot
{
    private MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();
    // private DriverController driverController = DriverController.getInstance();
    // private OperatorController operatorController = OperatorController.getInstance();

    private boolean isPreAutonomous = true;
    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    @Override
    public void robotInit()
    {
        System.out.println("2020-Robot-Development");
        //Test
    }

    /**
     * This function is called periodically.
     */
    @Override
    public void robotPeriodic()
    {
        mainShuffleboard.updateShuffleboardTime();
    }

    /**
     * This function is run once each time the robot enters autonomous mode.
     */
    @Override
    public void autonomousInit()
    {
        isPreAutonomous = false;
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic()
    {

    }

    /**
     * This function is called once each time the robot enters teleoperated mode.
     */
    @Override
    public void teleopInit()
    {
        mainShuffleboard.setDriverControllerSettings();
        mainShuffleboard.setOperatorControllerSettings();
    }

    /**
     * This function is called periodically during teleoperated mode.
     */
    @Override
    public void teleopPeriodic()
    {
        // System.out.println(driverController.getRawAxis(DriverController.Axis.kLeftX));
        // System.out.println(operatorController.getRawAxis(OperatorController.Axis.kXAxis));
    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    @Override
    public void testInit()
    {
        isPreAutonomous = true;
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic()
    {

    }

        /**
     * This function is called once each time the robot is disabled.
     */
    @Override
    public void disabledInit()
    {

    }

    /**
     * This function is called periodically when the robot is disabled.
     */
    @Override
    public void disabledPeriodic()
    {
        if(isPreAutonomous)
        {
            mainShuffleboard.checkForNewAutonomousTabData();
        }
    }
}
