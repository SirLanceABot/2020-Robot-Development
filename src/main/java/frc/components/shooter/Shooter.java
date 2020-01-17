package frc.components.shooter;

public class Shooter 
{
  double elevatorPosition = 0;
  private State currentState = State.Off;
  private static Button lastButtonPressed = null;
  private static Shooter instance = new Shooter();

  // ----------------------------------------------------------------------//
  private enum State {
    Off() {

      void doAction() {
        System.out.println("Testing Off");
      }
    },
    Searching() {

      void doAction() {
        System.out.println("Testing Holding");
      }
    },
    Aligning() {
        void doAction() {
          System.out.println("Testing Holding");
        }
      },
    Moving() {
      void doAction() {
        System.out.println("Testing Moving Up");
      }
    };
    abstract void doAction();

    State() {
    }


 
    
  }

  // ----------------------------------------------------------------------//
  private enum Event {
  }

  // ----------------------------------------------------------------------//
  private enum Button {
  }

  // ----------------------------------------------------------------------//
  private enum Transition {

    // Transition_01();
    // private final State currentState;
    // private final Event event;
    // private final State nextState;

    // Transition(State currentState, Event event, State nextState) {
    //   this.currentState = currentState;
    //   this.event = event;
    //   this.nextState = nextState;
    // }

    // // table lookup to determine new state given the current state and the event
    // private static State findNextState(State currentState, Event event) {
    //   for (Transition transition : Transition.values()) {
    //     if (transition.currentState == currentState && transition.event == event) {
    //       return transition.nextState;
    //     }
    //   }
    //   return currentState; // throw an error if here
    // }
  }


  private Shooter() {
    System.out.println(this.getClass().getName() + ": Started Constructing");
    System.out.println(this.getClass().getName() + ": Finished Constructing");
  }



  // ----------------------------------------------------------------------//
  public void ShooterFSM() 
  {

  }
}
