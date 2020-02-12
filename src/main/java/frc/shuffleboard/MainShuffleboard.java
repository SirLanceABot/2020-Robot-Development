package frc.shuffleboard;

import frc.shuffleboard.AutonomousTab.AutonomousTabData;

/**
 * The MainShuffleboard class will be the main interface to access the other
 * tabs on the Shuffleboard.
 * 
 * <p>
 * <b>Shuffleboard Settings</b>
 * <ul>
 * <li>Open the Shuffleboard</li>
 * <li>Open the File menu and select Preferences</li>
 * <li>Select App Settings on the left (if not already selected)</li>
 * <li>Under Tab Setting on the right, set the Default Tile Size to 32</li>
 * <li>Select a tab under Tabs on the left</li>
 * <li>Under Layout on the right, set Tile Size to 32, set Horizontal Spacing to
 * 16, set Vertical Spacing to 16</li>
 * </ul>
 */
public class MainShuffleboard
{
    private AutonomousTab autonomousTab = AutonomousTab.getInstance();
    private DriverControllerTab driverControllerTab = DriverControllerTab.getInstance();
    private OperatorControllerTab operatorControllerTab = OperatorControllerTab.getInstance();
    private CameraTab cameraTab = CameraTab.getInstance();

    private static MainShuffleboard instance = new MainShuffleboard();

    private MainShuffleboard()
    {

    }

    public static MainShuffleboard getInstance()
    {
        return instance;
    }

    // ----------------------------------------------------------------------------------
    // AUTONOMOUS TAB
    public AutonomousTabData getAutonomousTabData()
    {
        return autonomousTab.getAutonomousTabData();
    }

    public void checkForNewAutonomousTabData()
    {
        autonomousTab.checkForNewAutonomousTabData();
    }

    // ------------------------------------------------------------------------------------
    // DRIVER CONTROLLER TAB
    public void setDriverControllerSettings()
    {
       driverControllerTab.setDriverControllerAxisSettings();
    }

    // ------------------------------------------------------------------------------------
    // OPERATOR CONTROLLER TAB
    public void setOperatorControllerSettings()
    {
       operatorControllerTab.setOperatorControllerAxisSettings();
    }

    // ------------------------------------------------------------------------------------
    // CAMERA TAB
    public void updateShuffleboardTime()
    {
       cameraTab.updateShuffleboardTime();
    }
}
