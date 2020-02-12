package frc.vision;

/*
  UDP receive program

 Usage:
   Add this file or multiple files - or equivalents that actually route the messages to the right places - to the roboRIO
   Start the thread or multiple threads if each thread processes its own messages
   
   In a RPi test environment with no roboRIO use the below 5 statements in Main.java
   On the roboRIO use something similar in the right place (likely robot.java) and don't call it test!
   
    private static UdpReceive testUDPreceive;
    private static Thread UDPreceiveThread;

    // start test UDP receiver
    UDPreceive = new UdpReceive(5800); // port must match what the RPi is sending on
    UDPreceiveThread = new Thread(UDPreceive, "4237UDPreceive");
    UDPreceiveThread.start();

*/

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class UdpReceive implements Runnable
{
    private static final String pId = new String("[UdpReceive]");

    private static String lastDataReceived = "";
    private DatagramSocket socket = null;

    public UdpReceive(int port)
    {
        try
        {
            socket = new DatagramSocket(port);
            socket.setSoTimeout(500); // set receive timeout in milliseconds in case RPi is dead
        }
        catch (SocketException e)
        {
            // do something when something bad happens
            e.printStackTrace();
        }

        // Setting the port address to reuse can sometimes help and I can't think of how
        // it would hurt us if we only have one process running on a port.
        // Especially if the socket isn't closed, it can take some time to free the port
        // for reuse so if the program is restarted quickly and the port isn't noticed
        // to be free by the operating system,
        // there can be a socket exception if Reuse isn't set.
        // Example: socket = new DatagramSocket(port);
        // Example: socket.setReuseAddress(true);
    }

    public void run()
    {
        System.out.println(pId + " packet listener thread started");
        byte[] buf = new byte[1024];
        final int bufLength = buf.length; // save original length because length property is changed with usage
        DatagramPacket packet = new DatagramPacket(buf, bufLength);

        while (true)
        {
            try
            {
                // receive request
                packet.setLength(bufLength);
                socket.receive(packet); // always receive the packets
                byte[] data = packet.getData();
                lastDataReceived = new String(data, 0, packet.getLength());
                System.out.println(pId + " >" + lastDataReceived + "<");

                if (lastDataReceived.startsWith("Turret "))
                {
                    String message = new String(lastDataReceived.substring("Turret ".length()));
                    frc.robot.Robot.turretNext.fromJson(message);  
                }
                else if (lastDataReceived.startsWith("Intake "))
                {
                    String message = new String(lastDataReceived.substring("Intake ".length()));
                    frc.robot.Robot.intakeNext.fromJson(message); 
                }
                else
                {
                    System.out.println(pId + " Unknown class received UDP " + lastDataReceived);
                }
            } 
            catch (SocketTimeoutException e)
            {
                // do something when no messages for awhile
                System.out.println(pId + " hasn't heard from any vision pipeline for awhile");
            } 
            catch (IOException e)
            {
                e.printStackTrace();
                // could terminate loop but there is no easy restarting
            }
        }
        // socket.close();
    }
}