package frc.components.powercellsupervisor.shooter;

import frc.controls.OperatorController;
import frc.controls.Logitech.Button;
import frc.sensors.LidarLite.LIDAR_Lite;
public class Shooter 
{
  double elevatorPosition = 0;
  private static State currentState = State.Off;
  private static Button lastButtonPressed = null;
  private static Flywheel flywheel = Flywheel.getInstance();
  private static Turret turret = Turret.getInstance();
  private static Shroud shroud = Shroud.getInstance();
  private static LIDAR_Lite lidar = LIDAR_Lite.getInstance();
  private static double distanceToTarget;
  private static double angleToTarget;
  //private static Gate gate = Gate.getInstance();
  private static OperatorController operatorController = OperatorController.getInstance();

  private static Shooter instance = new Shooter();

  // ----------------------------------------------------------------------//
  private enum State 
  {
    Off() 
    {
      void doAction() 
      {
        System.out.println("State: Off");
        if(operatorController.getRawButton(2))
        {
          currentState = Transition.findNextState(currentState, Event.VisionAssistButtonPressed);
        }
      }
    },
    Searching() 
    {
      void doAction() 
      {
        System.out.println("State: Searching");
        turret.rotateToWall();
        //vision function that returns the boolean of if the tape is on the screen or not
        //if true
        currentState = Transition.findNextState(currentState, Event.TapeFound);
      }
    },
    Aligning() 
    {
        void doAction() 
        {
          System.out.println("State: Aligning");
          //vision code method that will get the relative angle of the
        }
      },
    Calculating() 
    {
      void doAction() 
      {
        System.out.println("State: Calculating");
      }
    },
    SettingTrajectory() 
    {
        void doAction() 
        {
          System.out.println("State: SettingTrajectory");
        }
    },
    PreShotCheck() 
    {
        void doAction() 
        {
          System.out.println("State: PreShotCheck");
        }
    },
    ShootingOnePowerCell() 
    {
        void doAction() 
        {
          System.out.println("State: ShootingOneBall");
        }
    },
    UserCorrection() 
    {
        void doAction() 
        {
          System.out.println("State: UserCorrection");
        }
    },
    ShootingRestOfShuttle()
    {
        void doAction() 
        {
          System.out.println("State: ShootingRestOfClip");
        }
    };


    abstract void doAction();

    State() {
    }


 
    
  }

  // ----------------------------------------------------------------------//
  private enum Event 
  {
    VisionAssistButtonPressed, 
    TapeFound, 
    AlignedWithTape, 
    ValuesCalulated,
    TrajectorySet,
    PreShotCheckPassed,
    PreShotCheckFailed,
    FirstPowerCellOnTarget,
    FirstPowerCellOffTarget,
    ShuttleEmpty;
  }

  // ----------------------------------------------------------------------//
  private enum Button 
  {
  }

  // ----------------------------------------------------------------------//
  private enum Transition 
  {
    //-----------Current State --------------------------Event---------------------------NextState------//
    Transition_01(State.Off,                    Event.VisionAssistButtonPressed,        State.Searching),
    // Transition_02(State.Off,                    Event.TapeFound,                        State.Off),
    // Transition_03(State.Off,                    Event.AlignedWithTape,                  State.Off),
    // Transition_04(State.Off,                    Event.ValuesCalulated,                  State.Off),
    // Transition_05(State.Off,                    Event.TrajectorySet,                    State.Off),
    // Transition_06(State.Off,                    Event.PreShotCheckPassed,               State.Off),
    // Transition_07(State.Off,                    Event.PreShotCheckFailed,               State.Off),
    // Transition_08(State.Off,                    Event.FirstPowerCellOnTarget,                State.Off),
    // Transition_09(State.Off,                    Event.FirstPowerCellOffTarget,               State.Off),
    // Transition_10(State.Off,                    Event.ShuttleEmpty,                        State.Off),
    // Transition_11(State.Searching,              Event.VisionAssistButtonPressed,        State.Off),
    Transition_12(State.Searching,              Event.TapeFound,                        State.Aligning),
    // Transition_13(State.Searching,              Event.AlignedWithTape,                  State.Aligning),
    // Transition_14(State.Searching,              Event.ValuesCalulated,                  State.Off),
    // Transition_15(State.Searching,              Event.TrajectorySet,                    State.Off),
    // Transition_16(State.Searching,              Event.PreShotCheckPassed,               State.Off),
    // Transition_17(State.Searching,              Event.PreShotCheckFailed,               State.Off),
    // Transition_18(State.Searching,              Event.FirstPowerCellOnTarget,                State.Off),
    // Transition_19(State.Searching,              Event.FirstPowerCellOffTarget,               State.Off),
    // Transition_20(State.Searching,              Event.ShuttleEmpty,                        State.Off),
    // Transition_21(State.Aligning,               Event.VisionAssistButtonPressed,        State.Aligning),
    // Transition_22(State.Aligning,               Event.TapeFound,                        State.Aligning),
    // Transition_23(State.Aligning,               Event.AlignedWithTape,                  State.Calculating),
    // Transition_24(State.Aligning,               Event.ValuesCalulated,                  State.Off),
    // Transition_25(State.Aligning,               Event.TrajectorySet,                    State.Off),
    // Transition_26(State.Aligning,               Event.PreShotCheckPassed,               State.Off),
    // Transition_27(State.Aligning,               Event.PreShotCheckFailed,               State.Off),
    // Transition_28(State.Aligning,               Event.FirstPowerCellOnTarget,                State.Off),
    // Transition_29(State.Aligning,               Event.FirstPowerCellOffTarget,               State.Off),
    // Transition_30(State.Aligning,               Event.ShuttleEmpty,                        State.Off),
    // Transition_31(State.Calculating,            Event.VisionAssistButtonPressed,        State.Calculating),
    // Transition_32(State.Calculating,            Event.TapeFound,                        State.Aligning),
    // Transition_33(State.Calculating,            Event.AlignedWithTape,                  State.Calculating),
    Transition_34(State.Calculating,            Event.ValuesCalulated,                  State.SettingTrajectory),
    // Transition_35(State.Calculating,            Event.TrajectorySet,                    State.Off),
    // Transition_36(State.Calculating,            Event.PreShotCheckPassed,               State.Off),
    // Transition_37(State.Calculating,            Event.PreShotCheckFailed,               State.Off),
    // Transition_38(State.Calculating,            Event.FirstPowerCellOnTarget,                State.Off),
    // Transition_39(State.Calculating,            Event.FirstPowerCellOffTarget,               State.Off),
    // Transition_40(State.Calculating,            Event.ShuttleEmpty,                        State.Off),
    // Transition_41(State.SettingTrajectory,      Event.VisionAssistButtonPressed,        State.Searching),
    // Transition_42(State.SettingTrajectory,      Event.TapeFound,                        State.Aligning),
    // Transition_43(State.SettingTrajectory,      Event.AlignedWithTape,                  State.Calculating),
    // Transition_44(State.SettingTrajectory,      Event.ValuesCalulated,                  State.SettingTrajectory),
    Transition_45(State.SettingTrajectory,      Event.TrajectorySet,                    State.PreShotCheck),
    // Transition_46(State.SettingTrajectory,      Event.PreShotCheckPassed,               State.Off),
    // Transition_47(State.SettingTrajectory,      Event.PreShotCheckFailed,               State.Off),
    // Transition_48(State.SettingTrajectory,      Event.FirstPowerCellOnTarget,                State.Off),
    // Transition_49(State.SettingTrajectory,      Event.FirstPowerCellOffTarget,               State.Off),
    // Transition_50(State.SettingTrajectory,      Event.ShuttleEmpty,                        State.Off),
    // Transition_51(State.PreShotCheck,           Event.VisionAssistButtonPressed,        State.Off),
    // Transition_52(State.PreShotCheck,           Event.TapeFound,                        State.Off),
    // Transition_53(State.PreShotCheck,           Event.AlignedWithTape,                  State.Off),
    // Transition_54(State.PreShotCheck,           Event.ValuesCalulated,                  State.Off),
    // Transition_55(State.PreShotCheck,           Event.TrajectorySet,                    State.Off),
    Transition_56(State.PreShotCheck,           Event.PreShotCheckPassed,               State.ShootingOnePowerCell),
    Transition_57(State.PreShotCheck,           Event.PreShotCheckFailed,               State.PreShotCheck),
    // Transition_58(State.PreShotCheck,           Event.FirstPowerCellOnTarget,                State.Off),
    // Transition_59(State.PreShotCheck,           Event.FirstPowerCellOffTarget,               State.Off),
    // Transition_60(State.PreShotCheck,           Event.ShuttleEmpty,                        State.Off),
    // Transition_61(State.ShootingOnePowerCell,        Event.VisionAssistButtonPressed,        State.Off),
    // Transition_62(State.ShootingOnePowerCell,        Event.TapeFound,                        State.Off),
    // Transition_63(State.ShootingOnePowerCell,        Event.AlignedWithTape,                  State.Off),
    // Transition_64(State.ShootingOnePowerCell,        Event.ValuesCalulated,                  State.Off),
    // Transition_65(State.ShootingOnePowerCell,        Event.TrajectorySet,                    State.Off),
    // Transition_66(State.ShootingOnePowerCell,        Event.PreShotCheckPassed,               State.Off),
    // Transition_67(State.ShootingOnePowerCell,        Event.PreShotCheckFailed,               State.Off),
    Transition_68(State.ShootingOnePowerCell,        Event.FirstPowerCellOnTarget,                State.ShootingRestOfShuttle),
    Transition_69(State.ShootingOnePowerCell,        Event.FirstPowerCellOffTarget,               State.UserCorrection),
    // Transition_70(State.ShootingOnePowerCell,        Event.VisionAssistButtonPressed,        State.Off),
    // Transition_71(State.UserCorrection,         Event.VisionAssistButtonPressed,        State.Off),
    // Transition_72(State.UserCorrection,         Event.TapeFound,                        State.Off),
    // Transition_73(State.UserCorrection,         Event.AlignedWithTape,                  State.Off),
    Transition_74(State.UserCorrection,         Event.ValuesCalulated,                  State.SettingTrajectory),
    // Transition_75(State.UserCorrection,         Event.TrajectorySet,                    State.Off),
    // Transition_76(State.UserCorrection,         Event.PreShotCheckPassed,               State.Off),
    // Transition_77(State.UserCorrection,         Event.PreShotCheckFailed,               State.Off),
    // Transition_78(State.UserCorrection,         Event.FirstPowerCellOnTarget,                State.Off),
    // Transition_79(State.UserCorrection,         Event.FirstPowerCellOffTarget,               State.Off),
    // Transition_80(State.UserCorrection,         Event.ShuttleEmpty,                        State.Off),
    // Transition_81(State.ShootingRestOfShuttle,     Event.VisionAssistButtonPressed,        State.Off),
    // Transition_82(State.ShootingRestOfShuttle,     Event.TapeFound,                        State.Off),
    // Transition_83(State.ShootingRestOfShuttle,     Event.AlignedWithTape,                  State.Off),
    // Transition_84(State.ShootingRestOfShuttle,     Event.ValuesCalulated,                  State.Off),
    // Transition_85(State.ShootingRestOfShuttle,     Event.TrajectorySet,                    State.Off),
    // Transition_86(State.ShootingRestOfShuttle,     Event.PreShotCheckPassed,               State.Off),
    // Transition_87(State.ShootingRestOfShuttle,     Event.PreShotCheckFailed,               State.Off),
    // Transition_88(State.ShootingRestOfShuttle,     Event.FirstPowerCellOnTarget,                State.Off),
    Transition_89(State.ShootingRestOfShuttle,     Event.ShuttleEmpty,                        State.Off);
    
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



  // ----------------------------------------------------------------------//
  public void ShooterFSM() 
  {
    currentState.doAction();
  }
}
