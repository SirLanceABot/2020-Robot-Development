package frc.shuffleboard;

public class DriverControllerTab
{

    private static DriverControllerTab instance = new DriverControllerTab();

    private DriverControllerTab()
    {

    }

    protected static DriverControllerTab getInstance()
    {
        return instance;
    }   
}
