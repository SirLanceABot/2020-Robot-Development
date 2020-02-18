package frc.vision;

public class Vision
{
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
    }
    private static boolean isConnected = false;

    private static UdpReceive udpReceive = new UdpReceive(5800); // port must match what the RPi is sending on;
    private static Thread udpReceiverThread = new Thread(udpReceive, "4237UDPreceive");
    static TargetDataB turretNext = new TargetDataB();
    static TargetDataE intakeNext = new TargetDataE();

    private static Vision instance = new Vision();
    
    private Vision()
    {
        udpReceiverThread.start();
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    public static Vision getInstance()
    {
        return instance;
    }

    public static void setIsConnected()
    {
        isConnected = true;
    }

    public static boolean isConnected()
    {
        return isConnected;
    }

    public TargetDataB getTurret()
    {
        return turretNext.get();
    }

    public TargetDataE getIntake()
    {
        return intakeNext.get();
    }
}