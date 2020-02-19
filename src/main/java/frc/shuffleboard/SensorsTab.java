package frc.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.components.drivetrain.Drivetrain;


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

    private NetworkTableEntry frontLeftDriveEntry;
    private NetworkTableEntry frontRightDriveEntry;
    private NetworkTableEntry backLeftDriveEntry;
    private NetworkTableEntry backRightDriveEntry;
    
    private static SensorsTab instance = new SensorsTab();

    private SensorsTab()
    {
        System.out.println(className + " : Constructor Started");

        // Create the text boxes for the Drivetrain encoders
        frontLeftDriveEntry = createTextBox("Front Left Drive",   0, 0, 0, 4, 2);
        frontRightDriveEntry = createTextBox("Front Right Drive", 0, 4, 0, 4, 2);
        backLeftDriveEntry = createTextBox("Back Left Drive",     0, 0, 2, 4, 2);
        backRightDriveEntry = createTextBox("Back Right Drive",   0, 4, 2, 4, 2);

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
    }
}