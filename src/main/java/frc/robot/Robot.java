/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.controls.DriverController;
import frc.controls.OperatorController;
import frc.controls.Xbox;
import frc.shuffleboard.MainShuffleboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot
{
    private static frc.vision.UdpReceive UDPreceiver;
    private static Thread UDPreceiverThread;
    private MainShuffleboard mainShuffleboard = MainShuffleboard.getInstance();
    private DriverController driverController = DriverController.getInstance();
    private OperatorController operatorController = OperatorController.getInstance();
    
    public static frc.vision.TargetDataB turretNext = new frc.vision.TargetDataB();
    public static frc.vision.TargetDataB turret = new frc.vision.TargetDataB();
    public static frc.vision.TargetDataE intakeNext = new frc.vision.TargetDataE();
    public static frc.vision.TargetDataE intake = new frc.vision.TargetDataE();

    public static CANSparkMax leftMotor = new CANSparkMax(3, MotorType.kBrushless);
    public static CANSparkMax rightMotor = new CANSparkMax(2, MotorType.kBrushless);


    private boolean isPreAutonomous = true;
    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    @Override
    public void robotInit()
    {
        System.out.println("2020-Robot-Development");
        // start test UDP receiver
        UDPreceiver = new frc.vision.UdpReceive(5800); // port must match what the RPi is sending on
        UDPreceiverThread = new Thread(UDPreceiver, "4237UDPreceive");
        UDPreceiverThread.start();
        leftMotor.restoreFactoryDefaults();
        rightMotor.restoreFactoryDefaults();
        rightMotor.setInverted(true);
        //Test
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
        driverController.checkRumbleEvent();

        System.out.println(driverController.getAction(DriverController.AxisAction.kMove));
        System.out.println(operatorController.getRawAxis(OperatorController.Axis.kXAxis));

        driverController.getRawButton(Xbox.Button.kStart);

        intake = intakeNext.get();
        turret = turretNext.get();

        if(turret.isFreshData())
        {
            if(turret.getAngleToTurn() > 1)
            {
                System.out.println("turning to the right" + "\t\tangle to turn: " + turret.getAngleToTurn());
                rightMotor.set(-0.05);
                leftMotor.set(0.05);
            }
            else if(turret.getAngleToTurn() < -1)
            {
                System.out.println("turning to the left" +  "\t\tangle to turn: " + turret.getAngleToTurn());
                rightMotor.set(0.05);
                leftMotor.set(-0.05);
            }
            else
            {
                System.out.print("it is off" + "\t\tangle to turn: " + turret.getAngleToTurn());
                rightMotor.set(0.0);
                leftMotor.set(0.0);
            }
        }


        //System.out.println(intake);
       // System.out.println(turret);
    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    @Override
    public void testInit()
    {
        isPreAutonomous = true;
        driverController.resetRumbleCounter();
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
            mainShuffleboard.updateMatchInfo();
            mainShuffleboard.checkForNewAutonomousTabData();
        }
    }
}
