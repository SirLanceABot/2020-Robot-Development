package frc.shuffleboard;

public class AutonomousTab
{

    private static AutonomousTab instance = new AutonomousTab();

    private AutonomousTab()
    {

    }

    protected static AutonomousTab getInstance()
    {
        return instance;
    }
}
