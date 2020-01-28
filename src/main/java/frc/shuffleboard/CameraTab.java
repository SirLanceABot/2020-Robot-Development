package frc.shuffleboard;

public class CameraTab
{

    private static CameraTab instance = new CameraTab();

    private CameraTab()
    {

    }

    protected static CameraTab getInstance()
    {
        return instance;
    }
}