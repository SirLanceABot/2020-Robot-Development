package frc.components.powercellsupervisor.intake;

import frc.components.powercellsupervisor.intake.Roller;
import frc.components.powercellsupervisor.intake.Wrist;
import frc.controls.DriverController;
import frc.controls.Xbox;

/**
 * Class to control the Intake subsystem
 * @author Maxwell Li   
 */
public class Intake
{
    private enum State 
    {
        Off()
        {
            @Override
            void doAction() 
            {
                roller.stop();
                if(driverController.getRawButton(Xbox.Button.kA))
                {
                    Transition.findNextState(currentState, Event.kIntakeButtonPressed);
                }
                else
                {
                    Transition.findNextState(currentState, Event.kNoPress);
                }
            }
        },
        Lowering()
        {
            @Override
            void doAction()
            {
                roller.stop();
                wrist.lower();
                /**
                 * if(wristIsLowered)
                 * Transition.findNextState(currentState, Event.Lowered)
                 */
            }
        },
        Raising()
        {
            @Override
            void doAction()
            {
                roller.stop();
                wrist.raise();
                /**
                 * if(wristIsLowered
                 * Transition.findNextState(currentState, Event.Raised)
                 */
            }
        },
        Intaking()
        {
            @Override
            void doAction() 
            {
                roller.intake();
                //TODO: Need to find or create libraries to find things such as steady state and other stuff (spikes)
                if(driverController.getRawButton(Xbox.Button.kA))
                {
                    Transition.findNextState(currentState, Event.kIntakeButtonPressed);
                }
                else
                {
                    Transition.findNextState(currentState, Event.kNoPress);
                }            
            }
        };
        
        abstract void doAction();

        State() {}
    }

    private enum Event
    {
        kIntakeButtonPressed, kNoPress, kLowered, kRaised;
    }

      // ----------------------------------------------------------------------//
    private enum Transition 
    {
        //-----------Current State --------------------------Event---------------------------NextState------//
        Transition_O_01(State.Off,                      Event.kNoPress,                         State.Off),
        Transition_O_02(State.Off,                      Event.kIntakeButtonPressed,             State.Lowering),
        Transition_O_03(State.Off,                      Event.kLowered,                         State.Off),
        Transition_O_04(State.Off,                      Event.kRaised,                          State.Off),

        Transition_R_01(State.Raising,                  Event.kNoPress,                         State.Raising),
        Transition_R_02(State.Raising,                  Event.kIntakeButtonPressed,             State.Lowering),
        Transition_R_03(State.Raising,                  Event.kLowered,                         State.Off),
        Transition_R_04(State.Raising,                  Event.kRaised,                          State.Off),
        
        Transition_L_01(State.Lowering,                 Event.kNoPress,                         State.Off),
        Transition_L_02(State.Lowering,                 Event.kIntakeButtonPressed,             State.Lowering),
        Transition_L_03(State.Lowering,                 Event.kLowered,                         State.Intaking),
        Transition_L_04(State.Lowering,                 Event.kRaised,                          State.Off),
        //TODO: Find out if we want to have the driver hold the button, or tap to toggle 
        //This will be achieved in states I 01 and I 02.
        Transition_I_01(State.Intaking,                 Event.kNoPress,                         State.Off),
        Transition_I_02(State.Intaking,                 Event.kIntakeButtonPressed,             State.Raising),
        Transition_I_03(State.Intaking,                 Event.kLowered,                         State.Intaking),
        Transition_I_04(State.Intaking,                 Event.kRaised,                          State.Off);

        private final State currentState;
        private final Event event;
        private final State nextState;

        Transition(State currentState, Event event, State nextState) {
        this.currentState = currentState;
        this.event = event;
        this.nextState = nextState;
        }

        // table lookup to determine new state given the current state and the event
        private static State findNextState(State currentState, Event event) {
        for (Transition transition : Transition.values()) {
            if (transition.currentState == currentState && transition.event == event) {
            return transition.nextState;
            }
        }
        return currentState; // throw an error if here
        }
    }

    private static Roller roller;
    private static Wrist wrist;
    private static DriverController driverController;
    private static State currentState = State.Off;
    private static Intake instance = new Intake();

    private Intake()
    {
        System.out.println(this.getClass().getName() + ": Started Constructing");
        roller = Roller.getInstance();
        wrist = Wrist.getInstance();
        driverController = DriverController.getInstance();
        System.out.println(this.getClass().getName() + ": Finished Constructing");
    }

    public static Intake getInstance()
    {
        return instance;
    }


    public void runFSM()
    {
        currentState.doAction();
    }


}