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
import frc.vision.UdpReceive;

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

    private static RobotState robotState = RobotState.kNone;

    private static Test test = Test.getInstance();
    private static Autonomous autonomous = Autonomous.getInstance();
    private static Disabled disabled = Disabled.getInstance();
    private static Teleop teleop = Teleop.getInstance();

    private static UdpReceive udpReceive = new UdpReceive(5800); // port must match what the RPi is sending on;
    private static Thread udpReceiverThread = new Thread(udpReceive, "4237UDPreceive");
    private MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();
    private DriverController driverController = DriverController.getInstance();
    private OperatorController operatorController = OperatorController.getInstance();

    private boolean isPreAutonomous = true;

    private static CANSparkMax leftMotor = new CANSparkMax(3, MotorType.kBrushless);
    private static CANSparkMax rightMotor = new CANSparkMax(2, MotorType.kBrushless);
    
    public Robot()
    {
        robotState = RobotState.kStartup;

        udpReceiverThread.start();
    }

    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    @Override
    public void robotInit()
    {
        System.out.println("2020-Robot-Development");
        leftMotor.restoreFactoryDefaults();
        rightMotor.restoreFactoryDefaults();
        rightMotor.setInverted(true);
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

    public static UdpReceive getUdpReceive()
    {
        return udpReceive;
    }
}
