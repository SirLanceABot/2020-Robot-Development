package frc.shuffleboard;

import frc.controls.DriverController;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;


/**
 * The DriverControllerTab class creates the Driver Controller tab on the Shuffleboard.
 */
public class DriverControllerTab
{
    private static final String className = new String("[DriverControllerTab]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    /**
     * The AxisObjects class is used to create the objects needed on the Shuffleboard and Network Table.
     */
    private class AxisObjects
    {
        private NetworkTableEntry deadzoneEntry;
        private NetworkTableEntry minOutputEntry;
        private NetworkTableEntry maxOutputEntry;
        private SendableChooser<Boolean> isFlipped = new SendableChooser<>();
        private SendableChooser<DriverController.AxisScale> axisScaleComboBox = new SendableChooser<>();
    }

    private DriverController driverController = DriverController.getInstance();

    // Create a Shuffleboard Tab
    private ShuffleboardTab driverControllerTab = Shuffleboard.getTab("Driver Controller");


    private AxisObjects leftXObjects = new AxisObjects();
    private AxisObjects leftYObjects = new AxisObjects();
    private AxisObjects rightXObjects = new AxisObjects();
    private AxisObjects rightYObjects = new AxisObjects();

    private static DriverControllerTab instance = new DriverControllerTab();

    private DriverControllerTab()
    {
        System.out.println(className + " : Constructor Started");

        createAxisWidgets(DriverController.Axis.kLeftX, "Left X", leftXObjects, 0);
        createAxisWidgets(DriverController.Axis.kLeftY, "Left Y", leftYObjects, 5);
        createAxisWidgets(DriverController.Axis.kRightX, "Right X", rightXObjects, 10);
        createAxisWidgets(DriverController.Axis.kRightY, "Right Y", rightYObjects, 15);

        // TODO: create the other widgets for the other axes

        System.out.println(className + ": Constructor Finished");
    }

    protected static DriverControllerTab getInstance()
    {
        return instance;
    }

    private void createAxisWidgets(DriverController.Axis axis, String name, AxisObjects axisObjects, int column)
    {
        int row = 0;
        int width = 4;
        int height = 2;

        // Get the current axis settings on the Driver Controller for the given axis
        DriverController.AxisSettings axisSettings = driverController.new AxisSettings();
        axisSettings = driverController.getAxisSettings(axis);

        // Create the text box to set the deadzone of the axis
        axisObjects.deadzoneEntry = createTextBox(name + " Deadzone", Double.toString(axisSettings.axisDeadzone), column, row, width, height);
        
        //Create the text box to set the min output of the axis
        row += 2;
        axisObjects.minOutputEntry = createTextBox(name + " Min Output", Double.toString(axisSettings.axisMinOutput), column, row, width, height);

        // Create the text box to set the max output of the axis
        row += 2;
        axisObjects.maxOutputEntry = createTextBox(name + " Max Output", Double.toString(axisSettings.axisMaxOutput), column, row, width, height);

        // Create the button to flip the axis (swap negative and positive)
        row += 2;
        createSplitButtonChooser(axisObjects.isFlipped, name + " Is Flipped", axisSettings.axisIsFlipped, column, row, width, height);

        // Create the combo box to set the axis scale
        row += 3;
        createComboBox(axisObjects.axisScaleComboBox, name + " Axis Scale", axisSettings.axisScale, column, row, width, height);
    }

    /**
    * Create a <b>Text Box</b>
    * <p>Create an entry in the Network Table and add the Text Box to the Shuffleboard Tab
    */
    private NetworkTableEntry createTextBox(String title, String defaultValue, int column, int row, int width, int height)
    {
        return driverControllerTab.add(title, defaultValue)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(column, row)
            .withSize(width, height)
            .getEntry();
    }

    /**
    * Create a <b>Combo Box</b>
    * <p>Create an entry in the Network Table and add the Combo Box to the Shuffleboard Tab
    */
    private void createComboBox(SendableChooser<DriverController.AxisScale> comboBox, String title, DriverController.AxisScale defaultValue, int column, int row, int width, int height)
    {
        SendableRegistry.add(comboBox, title);
        SendableRegistry.setName(comboBox, title);
        
        for(DriverController.AxisScale axisScale: DriverController.AxisScale.values())
        {
            if(axisScale == defaultValue)
            {
                comboBox.setDefaultOption(axisScale.toString(), axisScale);
            }
            else
            {
                comboBox.addOption(axisScale.toString(), axisScale);
            }
        }

        driverControllerTab.add(comboBox)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withPosition(column, row)
            .withSize(width, height);
    }

    /**
    * Create a <b>Split Button Chooser</b>
    * <p>Create an entry in the Network Table and add the Split Button Chooser to the Shuffleboard Tab
    */
    private void createSplitButtonChooser(SendableChooser<Boolean> splitButtonChooser, String title, boolean defaultValue, int column, int row, int width, int height)
    {
        SendableRegistry.add(splitButtonChooser, title);
        SendableRegistry.setName(splitButtonChooser, title);

        splitButtonChooser.setDefaultOption((defaultValue ? "Yes" : "No"), defaultValue);
        splitButtonChooser.addOption((!defaultValue ? "Yes" : "No"), !defaultValue);

        driverControllerTab.add(splitButtonChooser)
            .withWidget(BuiltInWidgets.kSplitButtonChooser)
            .withPosition(column, row)
            .withSize(width, height);
    }

    private DriverController.AxisSettings getAxisSettingsFromShuffleboard(AxisObjects axisObjects)
    {
        DriverController.AxisSettings axisSettings = driverController.new AxisSettings();

        axisSettings.axisDeadzone = Double.valueOf(axisObjects.deadzoneEntry.getString("0.1"));
        axisSettings.axisMinOutput = Double.valueOf(axisObjects.minOutputEntry.getString("0.0"));
        axisSettings.axisMaxOutput = Double.valueOf(axisObjects.maxOutputEntry.getString("1.0"));
        axisSettings.axisIsFlipped = axisObjects.isFlipped.getSelected();
        axisSettings.axisScale = axisObjects.axisScaleComboBox.getSelected();

        return axisSettings;
    }

    public void setDriverControllerAxisSettings()
    {
        DriverController.AxisSettings axisSettings = driverController.new AxisSettings();

        axisSettings = getAxisSettingsFromShuffleboard(leftXObjects);
        driverController.setAxisSettings(DriverController.Axis.kLeftX, axisSettings);

        axisSettings = getAxisSettingsFromShuffleboard(leftYObjects);
        driverController.setAxisSettings(DriverController.Axis.kLeftY, axisSettings);

        axisSettings = getAxisSettingsFromShuffleboard(rightXObjects);
        driverController.setAxisSettings(DriverController.Axis.kRightX, axisSettings);

        axisSettings = getAxisSettingsFromShuffleboard(rightYObjects);
        driverController.setAxisSettings(DriverController.Axis.kRightY, axisSettings);
        // axisSettings = getAxisSettingsFromShuffleboard(leftYObjects);
        // driverController.setAxisSettings(DriverController.Axis.kLeftY, axisSettings);

        // driverController.setAxisSettings(DriverController.Axis.kRightX, axisSettings);
        // driverController.setAxisSettings(DriverController.Axis.kRightY, axisSettings);

        
    }
}