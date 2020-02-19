package frc.components.powercellsupervisor;

public class PowerCellSupervisor
{
    private static final String className = new String("[PowerCellSupervisor]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private static PowerCellSupervisor powerCellSupervisor = new PowerCellSupervisor();

    public PowerCellSupervisor()
    {
        System.out.println(className + " : Constructor Started");

        System.out.println(className + ": Constructor Finished");
    }

    public static PowerCellSupervisor getInstance()
    {
        return powerCellSupervisor;
    }
}