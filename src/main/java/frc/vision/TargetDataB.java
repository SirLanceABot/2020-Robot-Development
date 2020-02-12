package frc.vision;

import com.google.gson.Gson;
import org.opencv.core.Point;
import org.opencv.core.Size;

/**
 * This class is used to store the target data. The user MUST MODIFY the
 * process() method. The user must create a new GripPipeline class using GRIP,
 * modify the TargetSelection class, and modify this class.
 * 
 * @author FRC Team 4237
 * @version 2019.01.28.14.20
 */
public class TargetDataB
{
    private static final String pId = new String("[TargetDataB]");

    // NOTE: No modifier means visible to both the class and package.

    // Target data that we need
    Point[] boundingBoxPts;
    Size imageSize;
    double portPositionInFrame, portDistance;
    double angleToTurn;

    // Point center;
    // Size size;
    // double angle;
    // public double fixedAngle;

    // These fields are used to track the validity of the data.
    int frameNumber; // Number of the camera frame
    boolean isFreshData; // Is the data fresh?
    boolean isTargetFound;

   /**
     * Default contructor - resets all of the target data.
     */
     public TargetDataB()
    {
        boundingBoxPts = new Point[4];
        imageSize = new Size();
        
        // center = new Point();
        // size = new Size();
        reset();
        frameNumber = 0;
    }

    /**
     * This method resets all of the target data, except the frameNumber. The user
     * MUST MODIFY
     */
    public synchronized void reset()
    {
        boundingBoxPts[0] = new Point(-1.0, -1.0);
        boundingBoxPts[1] = new Point(-1.0, -1.0);
        boundingBoxPts[2] = new Point(-1.0, -1.0);
        boundingBoxPts[3] = new Point(-1.0, -1.0);
        imageSize = new Size(-1.0, -1.0);
        portPositionInFrame = -1.0;
        portDistance = -1.0;
        angleToTurn = 0;

        // center.x = -1.0;
        // center.y = -1.0;
        // size.width = -1.0;
        // size.height = -1.0;
        // angle = -1.0;
        // fixedAngle = -1.0;
        isTargetFound = false;

        // DO NOT reset the frameNumber
        isFreshData = true;
        }

     /**
     * This method stores all of the target data.
     * 
     * @param targetData
     *                       The new target data to store.
     */
    public synchronized void set(TargetDataB targetData)
    {
        boundingBoxPts[0].x = targetData.boundingBoxPts[0].x;
        boundingBoxPts[0].y = targetData.boundingBoxPts[0].y;
        boundingBoxPts[1].x = targetData.boundingBoxPts[1].x;
        boundingBoxPts[1].y = targetData.boundingBoxPts[1].y;
        boundingBoxPts[2].x = targetData.boundingBoxPts[2].x;
        boundingBoxPts[2].y = targetData.boundingBoxPts[2].y;
        boundingBoxPts[3].x = targetData.boundingBoxPts[3].x;
        boundingBoxPts[3].y = targetData.boundingBoxPts[3].y;
        imageSize.width = targetData.imageSize.width;
        imageSize.height = targetData.imageSize.height;
        portPositionInFrame = targetData.portPositionInFrame;
        portDistance = targetData.portDistance;
        angleToTurn = targetData.angleToTurn;

        // center.x = targetData.center.x;
        // center.y = targetData.center.y;
        // size.width = targetData.size.width;
        // size.height = targetData.size.height;
        // angle = targetData.angle;
        // fixedAngle = targetData.fixedAngle;
        isTargetFound = targetData.isTargetFound;
        frameNumber = targetData.frameNumber;

        // DO NOT MODIFY this value.
        isFreshData = true;
 
        // System.out.println(pId + " " + center.x + " " + center.y);
    }

     /**
     * This method returns all of the target data.
     * 
     * @return The target data.
     */
    public synchronized TargetDataB get()
    {
        TargetDataB targetData = new TargetDataB();
        targetData.boundingBoxPts[0].x = boundingBoxPts[0].x;
        targetData.boundingBoxPts[0].y = boundingBoxPts[0].y;
        targetData.boundingBoxPts[1].x = boundingBoxPts[1].x;
        targetData.boundingBoxPts[1].y = boundingBoxPts[1].y;
        targetData.boundingBoxPts[2].x = boundingBoxPts[2].x;
        targetData.boundingBoxPts[2].y = boundingBoxPts[2].y;
        targetData.boundingBoxPts[3].x = boundingBoxPts[3].x;
        targetData.boundingBoxPts[3].y = boundingBoxPts[3].y;
        targetData.imageSize.width = imageSize.width;
        targetData.imageSize.height = imageSize.height;
        targetData.portPositionInFrame = portPositionInFrame;
        targetData.portDistance = portDistance;
        targetData.angleToTurn = angleToTurn;

        // targetData.center.x = center.x;
        // targetData.center.y = center.y;
        // targetData.size.width = size.width;
        // targetData.size.height = size.height;
        // targetData.angle = angle;
        // targetData.fixedAngle = fixedAngle;
        targetData.isTargetFound = isTargetFound;
        targetData.frameNumber = frameNumber;
        targetData.isFreshData = isFreshData;

        // Indicate that the data is no longer fresh data.
        isFreshData = false;

        // System.out.println(pId + " " + center.x + " " + center.y);

        return targetData;
    }

    /**
     * This method increments the frame number of the target data.
     */
    public synchronized void incrFrameNumber()
    {
            frameNumber++;
    }

    public synchronized Point[] getBoundingBoxPts()
    {
        return boundingBoxPts;
    }

    public synchronized Size getImageSize()
    {
        return imageSize;
    }

    /*
    public synchronized  Point getCenter()
    {
        return center;
    }

    public synchronized  Size getSize()
    {
        return size;
    }

    public synchronized  double getAngle()
    {
        return angle;
    }

    public synchronized double getFixedAngle()
    {
        return fixedAngle;
    }
    */

   /**
     * This method indicates if a target was found.
     * 
     * @return True if target is found. False if target is not found.
     */
    public synchronized boolean isTargetFound()
    {
        return isTargetFound;
    }

    /**
     * This method returns the frame number of the image.
     * 
     * @return The frame number of the camera image, starting at 1 and incrementing
     *         by 1.
     */
    public synchronized int getFrameNumber()
    {
        return frameNumber;
    }

    /**
     * This method indicates if the target data is fresh (new).
     * 
     * @return True if data is fresh. False is data is not fresh.
     */
    public synchronized boolean isFreshData()
    {
        return isFreshData;
    }

    public synchronized void fromJson(String message)
    {
        TargetDataB temp = new Gson().fromJson(message, TargetDataB.class);
        set(temp);
    }

    public synchronized String toJson()
    {
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        String json = gson.toJson(this); // serializes target to Json
        return json;
    }

    /**
     * This method converts the data to a string format for output.
     * 
     * @return The string to display.
     */
    public synchronized String toString()
    {
       return String.format("Frame = %d, %s, boundingBoxPts = [{%f, %f}, {%f, %f}, {%f, %f}, {%f, %f}],\nimageSize.width = %f, imageSize.height = %f,\nportPositionInFrame = %f, portDistance = %f,\nangleToTurn = %f %s", 
            frameNumber, isTargetFound ? "target" : "no target",
            boundingBoxPts[0].x, boundingBoxPts[0].y, boundingBoxPts[1].x, boundingBoxPts[1].y,
            boundingBoxPts[2].x, boundingBoxPts[2].y, boundingBoxPts[3].x, boundingBoxPts[3].y,
            imageSize.width, imageSize.height, portPositionInFrame, portDistance, angleToTurn, isFreshData ? "FRESH" : "stale");
        /*
        return String.format("Frame = %d, %s, center.x = %f, center.y = %f, size.width = %f, size.height = %f, angle = %f, fixedAngle = %f %s", 
            frameNumber, isTargetFound ? "target" : "no target",
            center.x, center.y, size.width, size.height, angle, fixedAngle, isFreshData ? "FRESH" : "stale");
            */
    }
}
