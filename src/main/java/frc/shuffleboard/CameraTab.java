package frc.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class CameraTab
{
    // Camera Tab
    private ShuffleboardTab cameraTab = Shuffleboard.getTab("Camera");

    private NetworkTableEntry timeRemainingEntry;
    private NetworkTableEntry matchEntry;
    private NetworkTableEntry teamStationEntry;

    // private int oldTime = 0;
    private double oldTime = 0.0;

    private static DriverStation dStation = DriverStation.getInstance();;
    
    private static CameraTab instance = new CameraTab();

    private CameraTab()
    {
        createCameraTab();
        updateCameraTab();
    }

    protected static CameraTab getInstance()
    {
        return instance;
    }

    private void createCameraTab()
    {
        // Camera widgets created on Rasp Pi
        timeRemainingEntry = 
            cameraTab.add("Time", "NA")
                .withWidget(BuiltInWidgets.kTextView)
                .withPosition(0,0)//20, 13)
                .withSize(4, 2)
                .getEntry();

        matchEntry = 
            cameraTab.add("Match", "NA")
                .withWidget(BuiltInWidgets.kTextView)
                .withPosition(0,4)//24, 13)
                .withSize(4, 2)
                .getEntry();

        teamStationEntry =
            cameraTab.add("Alliance", "NA")
                .withWidget(BuiltInWidgets.kTextView)
                .withPosition(0,8)//28, 13)
                .withSize(4, 2)
                .getEntry();
    }
    
    public void updateShuffleboardTime()
    {
        double delay = 0.2;
        
        int minutes;
        double seconds;

        double matchTime = dStation.getMatchTime();;
        String timeRemainingFormatted;

        // matchTime = (int)dStation.getMatchTime(); 

        if (matchTime >= oldTime + delay)
        {
            oldTime = matchTime;
            minutes = (int) (matchTime / 60.0);
            seconds = Math.round((matchTime - minutes * 60.0) * 10.0) / 10.0;
            timeRemainingFormatted = Integer.toString(minutes) + ":" + Double.toString(seconds);
            // timeRemainingFormatted = Double.toString(matchTime);

            timeRemainingEntry.setString(timeRemainingFormatted);
        }
    }

    public void updateCameraTab()
    {
        String matchString;
        String teamStationString;

        updateShuffleboardTime();

        matchString = dStation.getMatchType() + " " + dStation.getMatchNumber();
        teamStationString = dStation.getAlliance() + " " + dStation.getLocation();

        matchEntry.setString(matchString);
        teamStationEntry.setString(teamStationString);
    }
}