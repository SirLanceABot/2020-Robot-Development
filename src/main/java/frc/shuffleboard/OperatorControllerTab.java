package frc.shuffleboard;

public class OperatorControllerTab
{
    
    private static OperatorControllerTab instance = new OperatorControllerTab();

    private OperatorControllerTab()
    {

    }

    protected static OperatorControllerTab getInstance()
    {
        return instance;
    }    
}
