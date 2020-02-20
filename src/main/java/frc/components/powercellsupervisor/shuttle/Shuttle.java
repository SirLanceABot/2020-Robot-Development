package frc.components.powercellsupervisor.shuttle;

import frc.robot.Port;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.controls.OperatorController;
import frc.controls.DriverController;
/**
 * Class for controlling the shuttling of powercells from the intake to 
 * the shooter system.
 * Uses a 550 neo
 * @author Maxwell Li
 */
public class Shuttle
{
    private static final String className = new String("[Shuttle]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    /**
     * Enum for the states that the component can operate within
     */
    private enum State
    {
        Off()
        {
            @Override
            void doAction() 
            {   
                if(OperatorController.getInstance().getRawButton(1)) //assign the correct button
                {
                    currentState = Transition.findNextState(currentState, Event.ReadyToFeed);
                }
                if(!sensor5.get())
                {
                    currentState = Transition.findNextState(currentState, Event.PowerCellAtFlywheel);
                }       
                System.out.println("Shuttle State: Off");
                stopShuttle();
                if(isFull())
                {
                    currentState = Transition.findNextState(currentState, Event.ShuttleFull);
                }       
                else if(!sensor1.get())
                {
                    currentState = Transition.findNextState(currentState, Event.PowerCellReadyToShuttle);
                }
                else
                {                    
                    currentState = Transition.findNextState(currentState, Event.NoPowerCellReadyToShuttle);
                } 
            }
        },
        MovingOnePosition()
        {

            @Override
            void doAction() 
            {
                if(initFlag)
                {
                    currentPosition = getEncoderPosition();
                    targetPosition = currentPosition + (100/7.0);
                    initFlag = false;
                }

                currentPosition = getEncoderPosition();
                if(!sensor5.get())
                {
                    System.out.println("Powercell at Flywheel");
                    currentState = Transition.findNextState(currentState, Event.PowerCellAtFlywheel);
                    initFlag = true;
                }       
                if(isFull())
                {
                    System.out.println("Shuttle Full");
                    currentState = Transition.findNextState(currentState, Event.ShuttleFull);
                    initFlag = true;
                }       

                System.out.println("Shuttle State: MovingOnePosition" + "\tTargetPosition: " + targetPosition + "\tCurrent Pos: " + currentPosition);

                if(currentPosition < targetPosition - 3)
                {
                    motor.set(0.25);
                }
                else if(currentPosition > targetPosition + 3)
                {
                    motor.set(-0.25);
                }
                else
                {
                    System.out.println("No Powercell ready");
                    currentState = Transition.findNextState(currentState, Event.NoPowerCellReadyToShuttle);
                    initFlag = true;
                }
                //TODO: Add PID Position Control
            }
        },
        UnloadingShuttle()
        {
            @Override
            void doAction() 
            {
                System.out.println("Shuttle State: Unloading");
                if(!isEmpty())
                {
                    setSpeed(0.50); //TODO: Find the right feed speed
                }
                else
                {
                    stopShuttle();
                    currentState = Transition.findNextState(currentState, Event.ShuttleEmpty);
                }
            }
        },
        Full()
        {
            @Override
            void doAction() 
            {
                if(!sensor5.get())
                {
                    currentState = Transition.findNextState(currentState, Event.PowerCellAtFlywheel);
                }       
                System.out.println("Shuttle State: Full");
                if(isFull())
                {
                    stopShuttle();
                }


                if(OperatorController.getInstance().getRawButton(1)) //assign the correct button
                {
                    currentState = Transition.findNextState(currentState, Event.ReadyToFeed);
                }
                // else
                // {
                //     if(sensor1.get())
                //     {
                //         currentState = Transition.findNextState(currentState, Event.PowerCellReadyToShuttle);
                //     }
                //     else
                //     {                    
                //         currentState = Transition.findNextState(currentState, Event.NoPowerCellReadyToShuttle);
                //     }
                // }
            }
        },
        Empty()
        {
            @Override
            void doAction()
            {
                if(!sensor5.get())
                {
                    currentState = Transition.findNextState(currentState, Event.PowerCellAtFlywheel);
                }       
                System.out.println("Shuttle State: Empty");
                if(!sensor1.get())
                {
                    currentState = Transition.findNextState(currentState, Event.PowerCellReadyToShuttle);
                }
                else
                {                    
                    currentState = Transition.findNextState(currentState, Event.NoPowerCellReadyToShuttle);
                }
            }
        };

        abstract void doAction();
        State() {}
    }

    /**
     * Event enum that contains all the events that the machine can go through
     */
    private enum Event
    {
        PowerCellReadyToShuttle, ShuttleEmpty, ShuttleFull, NoPowerCellReadyToShuttle, PowerCellAtFlywheel, ReadyToFeed;
    }

    /**
     * Transition enum for the transition table that will direct the states
     */
    private enum Transition
    {
        //-----------------------------------------------TRANSITION TABLE---------------------------------------------------
        //----Name-----Current State--------------------------Event------------------------------------Target State---------
        TRANSITION_O_1(State.Off,                       Event.PowerCellReadyToShuttle,              State.MovingOnePosition),
        // TRANSITION_O_2(State.Off,                       Event.ShuttleEmpty,                         State.Off),
        TRANSITION_O_3(State.Off,                       Event.ShuttleFull,                          State.Full),
        TRANSITION_O_4(State.Off,                       Event.NoPowerCellReadyToShuttle,            State.Off),
        TRANSITION_O_5(State.Off,                       Event.PowerCellAtFlywheel,                  State.Off),
        TRANSITION_O_6(State.Off,                       Event.ReadyToFeed,                          State.UnloadingShuttle),
        
        // TRANSITION_M_1(State.MovingOnePosition,         Event.PowerCellReadyToShuttle,              State.MovingOnePosition),
        // TRANSITION_M_2(State.MovingOnePosition,         Event.ShuttleEmpty,                         State.Off),
        TRANSITION_M_3(State.MovingOnePosition,         Event.ShuttleFull,                          State.Full),
        TRANSITION_M_4(State.MovingOnePosition,         Event.NoPowerCellReadyToShuttle,            State.Off),
        TRANSITION_M_5(State.MovingOnePosition,         Event.PowerCellAtFlywheel,                  State.Off),
        // TRANSITION_M_6(State.MovingOnePosition,         Event.ReadyToFeed,                          State.Off),

        // TRANSITION_U_1(State.UnloadingClip,             Event.PowerCellReadyToShuttle,              State.UnloadingClip),
        TRANSITION_U_2(State.UnloadingShuttle,             Event.ShuttleEmpty,                         State.Off),
        // TRANSITION_U_3(State.UnloadingClip,             Event.ShuttleFull,                          State.Off),
        // TRANSITION_U_4(State.UnloadingClip,             Event.NoPowerCellReadyToShuttle,            State.UnloadingClip),
        // TRANSITION_U_5(State.UnloadingClip,             Event.PowerCellAtFlywheel,                  State.Off),
        // TRANSITION_U_6(State.UnloadingClip,             Event.ReadyToFeed,                          State.Off),

        // TRANSITION_F_1(State.Full,                      Event.PowerCellReadyToShuttle,              State.Off),
        // TRANSITION_F_2(State.Full,                      Event.ShuttleEmpty,                         State.Off),
        // TRANSITION_F_3(State.Full,                      Event.ShuttleFull,                          State.Off),
        // TRANSITION_F_4(State.Full,                      Event.NoPowerCellReadyToShuttle,            State.Off),
        // TRANSITION_F_5(State.Full,                      Event.PowerCellAtFlywheel,                  State.Off),
        TRANSITION_F_6(State.Full,                      Event.ReadyToFeed,                          State.UnloadingShuttle),

        TRANSITION_E_1(State.Empty,                     Event.PowerCellReadyToShuttle,              State.MovingOnePosition);
        // TRANSITION_E_2(State.Empty,                     Event.ShuttleEmpty,                         State.Off),
        // TRANSITION_E_3(State.Empty,                     Event.ShuttleFull,                          State.Off),
        // TRANSITION_E_4(State.Empty,                     Event.NoPowerCellReadyToShuttle,            State.MovingOnePosition),
        // TRANSITION_E_5(State.Empty,                     Event.PowerCellAtFlywheel,                  State.Off),
        // TRANSITION_E_6(State.Empty,                     Event.ReadyToFeed,                          State.Off);

        //-------------------------------------------------------------------------------------------------------------------
        private final State currentState;
        private final Event event;
        private final State nextState;
    
        /**
         * Constructor for transitions, shouldn't have to add any as it is all self contained
         * @param currentState
         * @param event
         * @param nextState
         */
        private Transition(State currentState, Event event, State nextState) 
        {
            this.currentState = currentState;
            this.event = event;
            this.nextState = nextState;
        }

        /**
         * table lookup to determine new state given the current state and the event
         * There is room for optimization
         * @param currentState
         * @param event
         * @return the next state (NEED TO STORE TO CURRENT STATE VARIABLE)
         */
        private static State findNextState(State currentState, Event event) 
        {
            for (Transition transition : Transition.values()) 
            {
                if (transition.currentState == currentState && transition.event == event) 
                {
                    return transition.nextState;
                }
            }
            System.out.println("ERROR: NO STATE TO TRANSITION TO FOUND" + "\tCURRENT: " + currentState + "\tEVENT: " + event);
            return currentState; // throw an error if here
        }
    }

    private static final double TICKS_PER_ROTATION = 4096.0;

    private static double currentPosition = 0;
    private static double targetPosition = 0;
    private static double shuttleJogDistance = 100/7.0;
    private static boolean initFlag = true;
    private static CANSparkMax motor = new CANSparkMax(Port.Motor.SHUTTLE, MotorType.kBrushless);
    private static CANEncoder encoder = new CANEncoder(motor);
    private static CANPIDController pidController = new CANPIDController(motor);
    //private static double currentPosition;
    private static DigitalInput sensor1 = new DigitalInput(Port.Sensor.SHUTTLE_1);
    private static DigitalInput sensor2 = new DigitalInput(Port.Sensor.SHUTTLE_2);
    private static DigitalInput sensor3 = new DigitalInput(Port.Sensor.SHUTTLE_3);
    private static DigitalInput sensor4 = new DigitalInput(Port.Sensor.SHUTTLE_4);
    private static DigitalInput sensor5 = new DigitalInput(Port.Sensor.SHUTTLE_5);
    private static DigitalInput sensor6 = new DigitalInput(Port.Sensor.SHUTTLE_6);
    private static State currentState = State.Off;
    private static DriverController controller = DriverController.getInstance();
    private static Shuttle instance = new Shuttle();

    protected Shuttle()
    {
        System.out.println(className + " : Constructor Started");

        motor.restoreFactoryDefaults();
        motor.setSmartCurrentLimit(40);
        setEncoderPosition(0);
        // currentPosition = 0;
        targetPosition = 0;

        System.out.println(className + ": Constructor Finished");
    }

    public static Shuttle getInstance()
    {
        return instance;
    }

    private static void setSpeed(double speed)
    {
        motor.set(speed);
    }
    
    private static void setSpeed()
    {
        motor.set(0.5);
    }

    private static void stopShuttle()
    {
        setSpeed(0.0);
    }

    public void stop()
    {
        setSpeed(0.0);
    }

    public void feedAllPowerCells()
    {
        if(!isEmpty())
        {
            setSpeed(0.5);
        }
    }
    
    public static double getEncoderPosition()
    {
        //System.out.println("initialized values");
        return encoder.getPosition();
    }

    /**
     * Returns the value of a sensor.
     * @param sensor The sensor you want the value from (1-6).
     * @return <b>sensorValue</b> The value of the sensor (true or false).
     */
    public static boolean getSensorValue(int sensor)
    {
        boolean sensorValue = true;

        switch(sensor)
        {
            case 1:
                sensorValue = sensor1.get();
                break;
            case 2:
                sensorValue = sensor2.get();
                break;
            case 3:
                sensorValue = sensor3.get();
                break;
            case 4:
                sensorValue = sensor4.get();
                break;
            case 5:
                sensorValue = sensor5.get();
                break;
            case 6:
                sensorValue = sensor6.get();
                break;
        }

        return sensorValue;
    }
    
    private static void setEncoderPosition(double position)
    {
        encoder.setPosition(position);
    }

    private static boolean isFull()
    {
        if(!sensor1.get() && !sensor2.get() && !sensor3.get() && !sensor4.get() && !sensor5.get() && !sensor6.get())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static boolean isEmpty()
    {
        if(sensor1.get() && sensor2.get() && sensor3.get() && sensor4.get() && sensor5.get() && sensor6.get())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    // private static void setCurrentPosition(double position)
    // {
    //     currentPosition = position;
    // }

    // public static double getCurrentPosition()
    // {
    //     return currentPosition;
    // }

    public static void setTargetPosition(double position)
    {
        targetPosition = position;
    }

    public static double getTargetPosition()
    {
        return targetPosition;
    }

    private static double inchesToTicks(double inches)
    {
        return inches * TICKS_PER_ROTATION;
    }

    public void feedTopBall()
    {
        if(initFlag)
        {
            currentPosition = getEncoderPosition();
            targetPosition = currentPosition;
            if(sensor6.get())
            {
                targetPosition += shuttleJogDistance;
            }
            if(sensor5.get())
            {
                targetPosition += shuttleJogDistance * 2;
            }
            if(sensor4.get())
            {
                targetPosition += shuttleJogDistance * 3;
            }
            if(sensor3.get())
            {
                targetPosition += shuttleJogDistance * 4;
            }
            if(sensor2.get())
            {
                targetPosition += shuttleJogDistance * 5;
            }
            if(sensor1.get())
            {
                targetPosition += shuttleJogDistance * 6;
            }
            initFlag = false;
        }

        currentPosition = getEncoderPosition();     

        if(currentPosition < targetPosition - 3)
        {
            motor.set(0.25);
        }
        else if(currentPosition > targetPosition + 3)
        {
            motor.set(-0.25);
        }
        else
        {
            System.out.println("No Powercell ready");            
            initFlag = true;
        }       
    }

    public void runFSM()
    {
        currentState.doAction();
    }

 
}