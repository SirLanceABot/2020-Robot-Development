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

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private PowerCellSupervisor powerCellSupervisor = PowerCellSupervisor.getInstance();
    private Climber climber = Climber.getInstance();

    private NetworkTableEntry frontLeftDriveEntry;
    private NetworkTableEntry frontRightDriveEntry;
    private NetworkTableEntry backLeftDriveEntry;
    private NetworkTableEntry backRightDriveEntry;

    private NetworkTableEntry centerIntakeEntry;
    private NetworkTableEntry leftIntakeEntry;
    private NetworkTableEntry rightIntakeEntry;

    private NetworkTableEntry shuttleEntry;
    
    private NetworkTableEntry shooterEntry;

    private NetworkTableEntry shroudEntry;

    private NetworkTableEntry turretEntry;

    private NetworkTableEntry climberArmEntry;
    private NetworkTableEntry climberWinchEntry;


    private NetworkTableEntry wristExtendedEntry; 
    private NetworkTableEntry wristRetractedEntry; 

    private NetworkTableEntry shuttleSensor1Entry; 
    private NetworkTableEntry shuttleSensor2Entry; 
    private NetworkTableEntry shuttleSensor3Entry; 
    private NetworkTableEntry shuttleSensor4Entry; 
    private NetworkTableEntry shuttleSensor5Entry; 
    private NetworkTableEntry shuttleSensor6Entry; 
    
    private static SensorsTab instance = new SensorsTab();

    private SensorsTab()
    {
        System.out.println(className + " : Constructor Started");

        // Create the text boxes for the Drivetrain encoders
        frontLeftDriveEntry = createTextBox("Front Left Drive",   0, 0, 0, 4, 2);
        frontRightDriveEntry = createTextBox("Front Right Drive", 0, 4, 0, 4, 2);
        backLeftDriveEntry = createTextBox("Back Left Drive",     0, 0, 2, 4, 2);
        backRightDriveEntry = createTextBox("Back Right Drive",   0, 4, 2, 4, 2);

        centerIntakeEntry = createTextBox("Center Intake",        0, 13, 0, 4, 2);
        leftIntakeEntry = createTextBox("Left Intake",            0, 9, 0, 4, 2);
        rightIntakeEntry = createTextBox("Right Intake",          0, 17, 0, 4, 2);

        shuttleEntry = createTextBox("Shuttle",                   0, 0, 5, 4, 2);

        shroudEntry = createTextBox("Shroud",                     0, 4, 5, 4, 2);

        turretEntry = createTextBox("Turret",                     0, 8, 5, 4, 2);

        shooterEntry = createTextBox("Shooter",                   0, 12, 5, 4, 2);

        climberArmEntry = createTextBox("Climber Arm",            0, 22, 0, 4, 2);
        climberWinchEntry = createTextBox("Climber Winch",        0, 22, 2, 4, 2);



        wristExtendedEntry = createTextBox("Wrist Sensor 1",       0, 18, 5, 4, 2);
        wristRetractedEntry = createTextBox("Wrist Sensor 2",       0, 23, 5, 4, 2);

        shuttleSensor1Entry = createTextBox("Shuttle Sensor 1",   0, 0, 8, 4, 2);
        shuttleSensor2Entry = createTextBox("Shuttle Sensor 2",   0, 4, 8, 4, 2);
        shuttleSensor3Entry = createTextBox("Shuttle Sensor 3",   0, 8, 8, 4, 2);
        shuttleSensor4Entry = createTextBox("Shuttle Sensor 4",   0, 12, 8, 4, 2);
        shuttleSensor5Entry = createTextBox("Shuttle Sensor 5",   0, 16, 8, 4, 2);
        shuttleSensor6Entry = createTextBox("Shuttle Sensor 6",   0, 20, 8, 4, 2);


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

    public void updateEncoderValues()
    {
        frontLeftDriveEntry.setNumber(drivetrain.getFrontLeftPosition());
        frontRightDriveEntry.setNumber(drivetrain.getFrontRightPosition());
        backLeftDriveEntry.setNumber(drivetrain.getBackLeftPosition());
        backRightDriveEntry.setNumber(drivetrain.getBackRightPosition());

        //centerIntakeEntry.setNumber();
        //leftIntakeEntry.setNumber();
        //rightIntakeEntry.setNumber();

        //shuttleEntry.setNumber();

        //shroudEntry.setNumber();

        //turretEntry.setNumber();

        //shooterEntry.setNumber();

        //climberArmEntry.setNumber();
        //climberWinchEntry.setNumber();



        //wristExtendedEntry.setNumber();
        //wristRetractedEntry.setNumber();

        //shuttleSensor1Entry.setNumber();
        //shuttleSensor2Entry.setNumber();
        //shuttleSensor3Entry.setNumber();
        //shuttleSensor4Entry.setNumber();
        //shuttleSensor5Entry.setNumber();
        //shuttleSensor6Entry.setNumber();
    }
}