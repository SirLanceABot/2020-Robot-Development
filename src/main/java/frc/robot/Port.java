package frc.robot;

/**
 * @author Elliot Measel
 * Class to organize the ports used
 */
public class Port
{
    private static final String className = new String("[Port]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private Port()
    {
        System.out.println(className + " : Constructor Started");

        System.out.println(className + ": Constructor Finished");
    }

    public class Motor
    {
        public static final int DRIVETRAIN_FRONT_RIGHT = 1;
        public static final int DRIVETRAIN_BACK_RIGHT =  2;
        public static final int DRIVETRAIN_BACK_LEFT =   3;
        public static final int DRIVETRAIN_FRONT_LEFT =  4;

        public static final int INTAKE_CENTER =          5;
        public static final int INTAKE_LEFT =            6;
        public static final int INTAKE_RIGHT =           7;

        public static final int SHUTTLE =                8;

        public static final int SHOOTER_MASTER =         9;
        public static final int SHOOTER_SLAVE =         10;

        public static final int SHROUD =                11;

        public static final int TURRET =                12;

        public static final int CLIMBER_ARM =           13;
        public static final int CLIMBER_WINCH =         14;

        public static final int CONTROL_PANEL =         15; //TODO: declare control panel motor
    }

    public class Sensor
    {
        //TODO: bring in LIDAR constant under sensors.LidarLite.Constants.java
        
        public static final int WRIST_EXTEND_LIMIT_SWITCH = 0;
        public static final int WRIST_RETRACT_LIMIT_SWITCH = 1;

        //SHUTTLE_1 is the spot closest to the intake
        //SHUTTLE_5 is the spot closest to the shooter
        public static final int SHUTTLE_1 = 2;
        public static final int SHUTTLE_2 = 3;
        public static final int SHUTTLE_3 = 4;
        public static final int SHUTTLE_4 = 5;
        public static final int SHUTTLE_5 = 6;
        public static final int SHUTTLE_6 = 7; //TODO: declare 6th shuttle sensor
    }

    public class Controller
    {
        public static final int DRIVER =   0;
        public static final int OPERATOR = 1;
    }

    public class Pneumatic
    {
        public static final int SHIFTER_EXTEND =        0;
        public static final int SHIFTER_RETRACT =       1;

        public static final int INTAKE_EXTEND =         2;
        public static final int INTAKE_RETRACT =        3;

        //TODO: declare control panel pneumatics
        public static final int CONTROL_PANEL_EXTEND =  4;
        public static final int CONTROL_PANEL_RETRACT = 5;

        //TODO: declare cooling pneumatics
        public static final int DRIVERTRAIN_COOLING =   6;
    }
}