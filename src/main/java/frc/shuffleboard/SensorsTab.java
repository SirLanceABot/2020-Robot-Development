package frc.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.components.climber.Climber;
import frc.components.drivetrain.Drivetrain;
import frc.components.powercellsupervisor.PowerCellSupervisor;

/**
 * @author Elliot Measel
 * ShuffleBoard display for all sensors and encoders
 */
public class SensorsTab
{
    private static final String className = new String("[SensorsTab]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    // Create a Shuffleboard Tab
    private ShuffleboardTab sensorsTab = Shuffleboard.getTab("Sensors");

    private static Drivetrain drivetrain = Drivetrain.getInstance();
    private static PowerCellSupervisor powerCellSupervisor = PowerCellSupervisor.getInstance();
    private static Climber climber = Climber.getInstance();

    private static NetworkTableEntry frontLeftDriveEntry;
    private static NetworkTableEntry frontRightDriveEntry;
    private static NetworkTableEntry backLeftDriveEntry;
    private static NetworkTableEntry backRightDriveEntry;

    private static NetworkTableEntry centerIntakeEntry;
    private static NetworkTableEntry leftIntakeEntry;
    private static NetworkTableEntry rightIntakeEntry;

    private static NetworkTableEntry shuttleEntry;
    
    private static NetworkTableEntry shooterEntry;

    private static NetworkTableEntry shroudEntry;

    private static NetworkTableEntry turretEntry;

    private static NetworkTableEntry climberArmEntry;
    private static NetworkTableEntry climberWinchEntry;


    private static NetworkTableEntry wristExtendedEntry; 
    private static NetworkTableEntry wristRetractedEntry; 

    private static NetworkTableEntry shuttleSensor1Entry; 
    private static NetworkTableEntry shuttleSensor2Entry; 
    private static NetworkTableEntry shuttleSensor3Entry; 
    private static NetworkTableEntry shuttleSensor4Entry; 
    private static NetworkTableEntry shuttleSensor5Entry; 
    private static NetworkTableEntry shuttleSensor6Entry; 
    
    private static SensorsTab instance = new SensorsTab();

    private SensorsTab()
    {
        System.out.println(className + " : Constructor Started");

        // Create the text boxes for the Drivetrain encoders
        frontLeftDriveEntry = createTextBox("Front Left Drive",   "0", 0, 0, 4, 2);
        frontRightDriveEntry = createTextBox("Front Right Drive", "0", 4, 0, 4, 2);
        backLeftDriveEntry = createTextBox("Back Left Drive",     "0", 0, 2, 4, 2);
        backRightDriveEntry = createTextBox("Back Right Drive",   "0", 4, 2, 4, 2);

        centerIntakeEntry = createTextBox("Center Intake",        "0", 13, 0, 4, 2);
        leftIntakeEntry = createTextBox("Left Intake",            "0", 9, 0, 4, 2);
        rightIntakeEntry = createTextBox("Right Intake",          "0", 17, 0, 4, 2);

        shuttleEntry = createTextBox("Shuttle",                   "0", 0, 5, 4, 2);

        shroudEntry = createTextBox("Shroud",                     "0", 4, 5, 4, 2);

        turretEntry = createTextBox("Turret",                     "0", 8, 5, 4, 2);

        shooterEntry = createTextBox("Shooter",                   "0", 12, 5, 4, 2);

        climberArmEntry = createTextBox("Climber Arm",            "0", 22, 0, 4, 2);
        climberWinchEntry = createTextBox("Climber Winch",        "0", 22, 2, 4, 2);



        wristExtendedEntry = createTextBox("Wrist Sensor 1",      false, 18, 5, 4, 2);
        wristRetractedEntry = createTextBox("Wrist Sensor 2",     false, 23, 5, 4, 2);

        shuttleSensor1Entry = createTextBox("Shuttle Sensor 1",   false, 0, 8, 4, 2);
        shuttleSensor2Entry = createTextBox("Shuttle Sensor 2",   false, 4, 8, 4, 2);
        shuttleSensor3Entry = createTextBox("Shuttle Sensor 3",   false, 8, 8, 4, 2);
        shuttleSensor4Entry = createTextBox("Shuttle Sensor 4",   false, 12, 8, 4, 2);
        shuttleSensor5Entry = createTextBox("Shuttle Sensor 5",   false, 16, 8, 4, 2);
        shuttleSensor6Entry = createTextBox("Shuttle Sensor 6",   false, 20, 8, 4, 2);


        System.out.println(className + ": Constructor Finished");
    }

    public static SensorsTab getInstance()
    {
        return instance;
    }

    /**
    * Create a <b>Text Box</b>
    * <p>Create an entry in the Network Table and add the Text Box to the Shuffleboard Tab
    */
    private NetworkTableEntry createTextBox(String title, Integer defaultValue, int column, int row, int width, int height)
    {
        return sensorsTab.add(title, defaultValue)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(column, row)
            .withSize(width, height)
            .getEntry();
    }

    private NetworkTableEntry createTextBox(String title, Boolean defaultValue, int column, int row, int width, int height)
    {
        return sensorsTab.add(title, defaultValue)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(column, row)
            .withSize(width, height)
            .getEntry();
    }

    private NetworkTableEntry createTextBox(String title, String defaultValue, int column, int row, int width, int height)
    {
        return sensorsTab.add(title, defaultValue)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(column, row)
            .withSize(width, height)
            .getEntry();
    }

    public void updateEncoderValues()
    {
        frontLeftDriveEntry.setNumber(drivetrain.getFrontLeftPosition());
        frontRightDriveEntry.setNumber(drivetrain.getFrontRightPosition());
        backLeftDriveEntry.setNumber(drivetrain.getBackLeftPosition());
        backRightDriveEntry.setNumber(drivetrain.getBackRightPosition());

        centerIntakeEntry.setString(powerCellSupervisor.getCenterRollerData());
        leftIntakeEntry.setString(powerCellSupervisor.getLeftRollerData());
        rightIntakeEntry.setString(powerCellSupervisor.getRightRollerData());

        shuttleEntry.setNumber(powerCellSupervisor.getShuttleEncoderValue());

        shroudEntry.setNumber(powerCellSupervisor.getShroudEncoderValue());

        turretEntry.setNumber(powerCellSupervisor.getTurretEncoderValue());

        shooterEntry.setNumber(powerCellSupervisor.getFlywheelEncoderValue());

        climberArmEntry.setNumber(climber.getArmEncoderPosition());
        climberWinchEntry.setNumber(climber.getWinchEncoderPosition());



        wristExtendedEntry.setBoolean(powerCellSupervisor.getWristSensorExtended());
        wristRetractedEntry.setBoolean(powerCellSupervisor.getWristSensorRetracted());

        shuttleSensor1Entry.setBoolean(powerCellSupervisor.getShuttleSensor(1));
        shuttleSensor2Entry.setBoolean(powerCellSupervisor.getShuttleSensor(2));
        shuttleSensor3Entry.setBoolean(powerCellSupervisor.getShuttleSensor(3));
        shuttleSensor4Entry.setBoolean(powerCellSupervisor.getShuttleSensor(4));
        shuttleSensor5Entry.setBoolean(powerCellSupervisor.getShuttleSensor(5));
        shuttleSensor6Entry.setBoolean(powerCellSupervisor.getShuttleSensor(6));
    }
}