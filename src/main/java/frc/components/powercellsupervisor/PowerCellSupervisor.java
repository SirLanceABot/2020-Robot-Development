package frc.components.powercellsupervisor;

import frc.components.powercellsupervisor.intake.Intake;
import frc.components.powercellsupervisor.shooter.Shooter;
import frc.components.powercellsupervisor.shuttle.Shuttle;
import frc.controls.DriverController;
import frc.controls.OperatorController;

public class PowerCellSupervisor
{
    private static final String className = new String("[PowerCellSupervisor]");
    
    // Static Initializer Block
    static
    {
        System.out.println(className + " : Class Loading");
    }

    private enum State 
    {
        NeitherControlled()
        {
            @Override
            public void doAction()
            {
                System.out.println("State: Off");

            }
        },
        IntakeControlled()
        {
            @Override
            public void doAction()
            {
                System.out.println("State: Intake Controlled");
                shuttle.runFSM();
                intake.runFSM();
            }
        },
        ShooterControlled()
        {
            @Override
            public void doAction()
            {
                System.out.println("State: Shooter Controlled");
                shuttle.runFSM();
                shooter.runFSM();
            }
        };

      abstract void doAction();
  
      State() 
      {
      }
    }
  
    // ----------------------------------------------------------------------//
    private enum Event 
    {
        Intaking, ReadyToShoot, NotShooting;
    }
  
    // ----------------------------------------------------------------------//
  
    // ----------------------------------------------------------------------//
    private enum Transition 
    {
        Transition_O_01(State.NeitherControlled,              Event.Intaking,                   State.IntakeControlled),
        Transition_O_02(State.NeitherControlled,              Event.ReadyToShoot,               State.ShooterControlled),
        Transition_O_03(State.NeitherControlled,              Event.NotShooting,                State.IntakeControlled),

        Transition_I_01(State.IntakeControlled,               Event.Intaking,                   State.IntakeControlled),
        Transition_I_02(State.IntakeControlled,               Event.ReadyToShoot,               State.ShooterControlled),
        Transition_I_03(State.IntakeControlled,               Event.NotShooting,                State.IntakeControlled),

        Transition_S_01(State.ShooterControlled,              Event.Intaking,                   State.ShooterControlled),
        Transition_S_02(State.ShooterControlled,              Event.ReadyToShoot,               State.ShooterControlled),
        Transition_S_03(State.ShooterControlled,              Event.NotShooting,                State.IntakeControlled);

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
            return currentState; // throw an error if here
        }
    }

    private static DriverController driverController = DriverController.getInstance();
    private static OperatorController operatorController = OperatorController.getInstance();
    private static Shuttle shuttle = Shuttle.getInstance();
    private static Shooter shooter = Shooter.getInstance();
    private static Intake intake = Intake.getInstance();
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