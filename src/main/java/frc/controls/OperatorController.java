package frc.controls;

public class OperatorController extends Logitech
{
    private static OperatorController instance = new OperatorController();

    private OperatorController()
    {
        super(1);

        setAxisFlipped(Logitech.Axis.kYAxis, true);
    }

    public static OperatorController getInstance()
    {
        return instance;
    }
}