package frc.components.powercellsupervisor.shuttle;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * Class for controlling the shuttling of powercells from the intake to 
 * the shooter system.
 * Uses a 550 neo
 * @author Maxwell Li
 */
public class Shuttle
{
    private enum State
    {
        Off()
        {
            @Override
            void doAction() 
            {
                stop();        
            }
        },
        MovingOnePosition()
        {
            private double currentPosition = getEncoderPosition();
            private double targetPosition = currentPosition + inchesToTicks(7);
            @Override
            void doAction() 
            {
                //TODO: Add PID Position Control
            }
        },
        UnloadingClip()
        {
            @Override
            void doAction() 
            {
                // TODO Auto-generated method stub
                
            }
        },
        Full()
        {
            @Override
            void doAction() 
            {
                // TODO Auto-generated method stub
                
            }
        },
        Empty()
        {
            @Override
            void doAction()
            {
                // TODO Auto-generated method stub
                
            }
        };

        abstract void doAction();
        State() {}
    }

    private enum Event
    {
        PowerCellReadyToShuttle, ShuttleEmpty, ShuttleFull, NoPowerCellReadyToShuttle;
    }

    private enum Transition
    {
        //-----------------------------------------------TRANSITION TABLE---------------------------------------------------
        //----Name-----Current State--------------------------Event------------------------------------Target State---------
        TRANSITION_O_1(State.Off,                       Event.PowerCellReadyToShuttle,              State.MovingOnePosition),
        TRANSITION_O_2(State.Off,                       Event.ShuttleEmpty,                         State.Off),
        TRANSITION_O_3(State.Off,                       Event.ShuttleFull,                          State.Off),
        TRANSITION_O_4(State.Off,                       Event.NoPowerCellReadyToShuttle,            State.MovingOnePosition),
        
        TRANSITION_M_1(State.MovingOnePosition,         Event.PowerCellReadyToShuttle,              State.MovingOnePosition),
        TRANSITION_M_2(State.MovingOnePosition,         Event.ShuttleEmpty,                         State.Off),
        TRANSITION_M_3(State.MovingOnePosition,         Event.ShuttleFull,                          State.Off),
        TRANSITION_M_4(State.MovingOnePosition,         Event.NoPowerCellReadyToShuttle,            State.MovingOnePosition),

        TRANSITION_U_1(State.UnloadingClip,             Event.PowerCellReadyToShuttle,              State.UnloadingClip),
        TRANSITION_U_2(State.UnloadingClip,             Event.ShuttleEmpty,                         State.Off),
        TRANSITION_U_3(State.UnloadingClip,             Event.ShuttleFull,                          State.Off),
        TRANSITION_U_4(State.UnloadingClip,             Event.NoPowerCellReadyToShuttle,            State.UnloadingClip),

        TRANSITION_F_1(State.Full,                      Event.PowerCellReadyToShuttle,              State.Off),
        TRANSITION_F_2(State.Full,                      Event.ShuttleEmpty,                         State.Off),
        TRANSITION_F_3(State.Full,                      Event.ShuttleFull,                          State.Off),
        TRANSITION_F_4(State.Full,                      Event.NoPowerCellReadyToShuttle,            State.Off),

        TRANSITION_E_1(State.Empty,                     Event.PowerCellReadyToShuttle,              State.MovingOnePosition),
        TRANSITION_E_2(State.Empty,                     Event.ShuttleEmpty,                         State.Off),
        TRANSITION_E_3(State.Empty,                     Event.ShuttleFull,                          State.Off),
        TRANSITION_E_4(State.Empty,                     Event.NoPowerCellReadyToShuttle,            State.MovingOnePosition);
        //-------------------------------------------------------------------------------------------------------------------
        private final State currentState;
        private final Event event;
        private final State nextState;

        Transition(State currentState, Event event, State nextState) 
        {
            this.currentState = currentState;
            this.event = event;
            this.nextState = nextState;
        }

        // table lookup to determine new state given the current state and the event
        private static State findNextState(State currentState, Event event) 
        {
            for (Transition transition : Transition.values()) 
            {
                if (transition.currentState == currentState && transition.event == event) 
                {
                return transition.nextState;
                }
            }
            System.out.println("ERROR: NO STATE TO TRANSITION TO FOUND");
            return currentState; // throw an error if here
        }
    }

    private static final int MOTOR_ID = 1; //TOD0: Change to actual motor id
    private static final double TICKS_PER_ROTATION = 4096.0;

    private static CANSparkMax motor = new CANSparkMax(MOTOR_ID, MotorType.kBrushless);
    private static CANEncoder encoder = new CANEncoder(motor);
    private static CANPIDController pidController = new CANPIDController(motor);
    //private static double currentPosition;
    private static double targetPosition;
    private static Shuttle instance = new Shuttle();


    protected Shuttle()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        motor.restoreFactoryDefaults();
        encoder.setPosition(0);
        // currentPosition = 0;
        targetPosition = 0;
        System.out.println(this.getClass().getName() + ": Finished Constructing");
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

    private static void stop()
    {
        motor.set(0.0);
    }
    
    private static double getEncoderPosition()
    {
        return encoder.getPosition();
    }
    
    private static void setEncoderPosition(double position)
    {
        encoder.setPosition(position);
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


 
}