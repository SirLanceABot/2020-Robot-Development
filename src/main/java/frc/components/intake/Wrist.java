package frc.components.intake;

public class Wrist
{
    private static Wrist instance = new Wrist();

    private Wrist()
    {
        
    }

    protected static Wrist getInstance()
    {
        return instance;
    } 
}