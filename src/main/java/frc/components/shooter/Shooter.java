package frc.components.shooter;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Joystick;

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
    Holding() {

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
    Nothing, ButtonPressed, AtElevation
  }

  // ----------------------------------------------------------------------//
  private enum Button {
    RocketShipHatchPanelLow, RocketShipHatchPanelMiddle, RocketShipHatchPanelHigh, RocketShipCargoLow,
    RocketShipCargoMiddle, RocketShipCargoHigh, CargoShipCargo, CargoShipHatchPanel, ManualModeUpFast, ManualModeUpSlow,
    ManualModeDownFast, ManualModeDownSlow, None
  }

  // ----------------------------------------------------------------------//
  private enum Transition {
    Transition_01(State.Off, Event.Nothing, State.Off), Transition_02(State.Off, Event.ButtonPressed, State.Moving),
    Transition_04(State.Off, Event.AtElevation, State.Holding),
    Transition_05(State.Moving, Event.Nothing, State.Moving),
    Transition_06(State.Moving, Event.ButtonPressed, State.Moving),
    Transition_08(State.Moving, Event.AtElevation, State.Holding),
    Transition_09(State.Holding, Event.Nothing, State.Holding),
    Transition_10(State.Holding, Event.ButtonPressed, State.Moving),
    Transition_12(State.Holding, Event.AtElevation, State.Holding);
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


  private Shooter() {
    System.out.println(this.getClass().getName() + ": Started Constructing");
    System.out.println(this.getClass().getName() + ": Finished Constructing");
  }






//   private Event getNewEvent(Button currentButton) {
    
//   }

  private boolean checkManualMode(Button buttonEvent) {
    if (buttonEvent == Button.ManualModeUpSlow) {
      return true;
    } else if (buttonEvent == Button.ManualModeUpFast) {
      return true;
    } else if (buttonEvent == Button.ManualModeDownSlow) {
      return true;
    } else if (buttonEvent == Button.ManualModeDownFast) {
      return true;
    } else {
      return false;
    }
  }

  // ----------------------------------------------------------------------//
  public void ShooterFSM() 
  {

  }
}
