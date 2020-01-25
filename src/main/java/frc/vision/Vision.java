package frc.vision;

import frc.vision.LowCamera;
import frc.vision.HighCamera;

// TESTING THE CAMERA
import org.opencv.imgproc.Imgproc;
import edu.wpi.first.cameraserver.CameraServer;


/**
 * @author Annika Price
 */
public class Vision
{
    private static LowCamera lowCamera = LowCamera.getInstance();
    private static HighCamera highCamera = HighCamera.getInstance();

    private static Vision instance = new Vision();

    private Vision()
    {
    }

    public static Vision getInstance()
    {
        return instance;
    }

    public static void run()
    {
        lowCamera.detectPowerCell();
        highCamera.detectHighGoal();
    }
}