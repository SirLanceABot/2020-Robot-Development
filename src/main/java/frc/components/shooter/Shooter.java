package frc.components.shooter;

public class Shooter 
{
  double elevatorPosition = 0;
  private static State currentState = State.Off;
  private static Button lastButtonPressed = null;
  private static Flywheel flywheel = Flywheel.getInstance();
  private static Turret turret = Turret.getInstance();
  private static Shroud shroud = Shroud.getInstance();
  //private static Gate gate = Gate.getInstance();

  private static Shooter instance = new Shooter();

  // ----------------------------------------------------------------------//
  private enum State 
  {
    Off() 
    {
      void doAction() 
      {
        System.out.println("State: Off");
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
    ShootingOneBall() 
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
    ShootingRestOfClip()
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
    FirstBallOnTarget,
    FirstBallOffTarget,
    ClipEmpty;
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
    Transition_02(State.Off,                    Event.TapeFound,                        State.Searching),
    Transition_03(State.Off,                    Event.AlignedWithTape,                  State.Searching),
    Transition_04(State.Off,                    Event.ValuesCalulated,                  State.Searching),
    Transition_05(State.Off,                    Event.TrajectorySet,                    State.Searching),
    Transition_06(State.Off,                    Event.PreShotCheckPassed,               State.Searching),
    Transition_07(State.Off,                    Event.PreShotCheckFailed,               State.Searching),
    Transition_08(State.Off,                    Event.FirstBallOnTarget,                State.Searching),
    Transition_09(State.Off,                    Event.FirstBallOffTarget,               State.Searching),
    Transition_10(State.Off,                    Event.ClipEmpty,                        State.Searching),
    Transition_11(State.Searching,              Event.VisionAssistButtonPressed,        State.Searching),
    Transition_12(State.Searching,              Event.TapeFound,                        State.Searching),
    Transition_13(State.Searching,              Event.AlignedWithTape,                  State.Searching),
    Transition_14(State.Searching,              Event.ValuesCalulated,                  State.Searching),
    Transition_15(State.Searching,              Event.TrajectorySet,                    State.Searching),
    Transition_16(State.Searching,              Event.PreShotCheckPassed,               State.Searching),
    Transition_17(State.Searching,              Event.PreShotCheckFailed,               State.Searching),
    Transition_18(State.Searching,              Event.FirstBallOnTarget,                State.Searching),
    Transition_19(State.Searching,              Event.FirstBallOffTarget,               State.Searching),
    Transition_20(State.Searching,              Event.ClipEmpty,                        State.Searching),
    Transition_21(State.Aligning,               Event.VisionAssistButtonPressed,        State.Searching),
    Transition_22(State.Aligning,               Event.TapeFound,                        State.Searching),
    Transition_23(State.Aligning,               Event.AlignedWithTape,                  State.Searching),
    Transition_24(State.Aligning,               Event.ValuesCalulated,                  State.Searching),
    Transition_25(State.Aligning,               Event.TrajectorySet,                    State.Searching),
    Transition_26(State.Aligning,               Event.PreShotCheckPassed,               State.Searching),
    Transition_27(State.Aligning,               Event.PreShotCheckFailed,               State.Searching),
    Transition_28(State.Aligning,               Event.FirstBallOnTarget,                State.Searching),
    Transition_29(State.Aligning,               Event.FirstBallOffTarget,               State.Searching),
    Transition_30(State.Aligning,               Event.ClipEmpty,                        State.Searching),
    Transition_31(State.Calculating,            Event.VisionAssistButtonPressed,        State.Searching),
    Transition_32(State.Calculating,            Event.TapeFound,                        State.Searching),
    Transition_33(State.Calculating,            Event.AlignedWithTape,                  State.Searching),
    Transition_34(State.Calculating,            Event.ValuesCalulated,                  State.Searching),
    Transition_35(State.Calculating,            Event.TrajectorySet,                    State.Searching),
    Transition_36(State.Calculating,            Event.PreShotCheckPassed,               State.Searching),
    Transition_37(State.Calculating,            Event.PreShotCheckFailed,               State.Searching),
    Transition_38(State.Calculating,            Event.FirstBallOnTarget,                State.Searching),
    Transition_39(State.Calculating,            Event.FirstBallOffTarget,               State.Searching),
    Transition_40(State.Calculating,            Event.ClipEmpty,                        State.Searching),
    Transition_41(State.SettingTrajectory,      Event.VisionAssistButtonPressed,        State.Searching),
    Transition_42(State.SettingTrajectory,      Event.TapeFound,                        State.Searching),
    Transition_43(State.SettingTrajectory,      Event.AlignedWithTape,                  State.Searching),
    Transition_44(State.SettingTrajectory,      Event.ValuesCalulated,                  State.Searching),
    Transition_45(State.SettingTrajectory,      Event.TrajectorySet,                    State.Searching),
    Transition_46(State.SettingTrajectory,      Event.PreShotCheckPassed,               State.Searching),
    Transition_47(State.SettingTrajectory,      Event.PreShotCheckFailed,               State.Searching),
    Transition_48(State.SettingTrajectory,      Event.FirstBallOnTarget,                State.Searching),
    Transition_49(State.SettingTrajectory,      Event.FirstBallOffTarget,               State.Searching),
    Transition_50(State.SettingTrajectory,      Event.ClipEmpty,                        State.Searching),
    Transition_51(State.PreShotCheck,           Event.VisionAssistButtonPressed,        State.Searching),
    Transition_52(State.PreShotCheck,           Event.TapeFound,                        State.Searching),
    Transition_53(State.PreShotCheck,           Event.AlignedWithTape,                  State.Searching),
    Transition_54(State.PreShotCheck,           Event.ValuesCalulated,                  State.Searching),
    Transition_55(State.PreShotCheck,           Event.TrajectorySet,                    State.Searching),
    Transition_56(State.PreShotCheck,           Event.PreShotCheckPassed,               State.Searching),
    Transition_57(State.PreShotCheck,           Event.PreShotCheckFailed,               State.Searching),
    Transition_58(State.PreShotCheck,           Event.FirstBallOnTarget,                State.Searching),
    Transition_59(State.PreShotCheck,           Event.FirstBallOffTarget,               State.Searching),
    Transition_60(State.PreShotCheck,           Event.ClipEmpty,                        State.Searching),
    Transition_61(State.ShootingOneBall,        Event.VisionAssistButtonPressed,        State.Searching),
    Transition_62(State.ShootingOneBall,        Event.TapeFound,                        State.Searching),
    Transition_63(State.ShootingOneBall,        Event.AlignedWithTape,                  State.Searching),
    Transition_64(State.ShootingOneBall,        Event.ValuesCalulated,                  State.Searching),
    Transition_65(State.ShootingOneBall,        Event.TrajectorySet,                    State.Searching),
    Transition_66(State.ShootingOneBall,        Event.PreShotCheckPassed,               State.Searching),
    Transition_67(State.ShootingOneBall,        Event.PreShotCheckFailed,               State.Searching),
    Transition_68(State.ShootingOneBall,        Event.FirstBallOnTarget,                State.Searching),
    Transition_69(State.ShootingOneBall,        Event.FirstBallOffTarget,               State.Searching),
    Transition_70(State.ShootingOneBall,        Event.VisionAssistButtonPressed,        State.Searching),
    Transition_71(State.UserCorrection,         Event.VisionAssistButtonPressed,        State.Searching),
    Transition_72(State.UserCorrection,         Event.TapeFound,                        State.Searching),
    Transition_73(State.UserCorrection,         Event.AlignedWithTape,                  State.Searching),
    Transition_74(State.UserCorrection,         Event.ValuesCalulated,                  State.Searching),
    Transition_75(State.UserCorrection,         Event.TrajectorySet,                    State.Searching),
    Transition_76(State.UserCorrection,         Event.PreShotCheckPassed,               State.Searching),
    Transition_77(State.UserCorrection,         Event.PreShotCheckFailed,               State.Searching),
    Transition_78(State.UserCorrection,         Event.FirstBallOnTarget,                State.Searching),
    Transition_79(State.UserCorrection,         Event.FirstBallOffTarget,               State.Searching),
    Transition_80(State.UserCorrection,         Event.ClipEmpty,                        State.Searching),
    Transition_81(State.ShootingRestOfClip,     Event.VisionAssistButtonPressed,        State.Searching),
    Transition_82(State.ShootingRestOfClip,     Event.TapeFound,                        State.Searching),
    Transition_83(State.ShootingRestOfClip,     Event.AlignedWithTape,                  State.Searching),
    Transition_84(State.ShootingRestOfClip,     Event.ValuesCalulated,                  State.Searching),
    Transition_85(State.ShootingRestOfClip,     Event.TrajectorySet,                    State.Searching),
    Transition_86(State.ShootingRestOfClip,     Event.PreShotCheckPassed,               State.Searching),
    Transition_87(State.ShootingRestOfClip,     Event.PreShotCheckFailed,               State.Searching),
    Transition_88(State.ShootingRestOfClip,     Event.FirstBallOnTarget,                State.Searching),
    Transition_89(State.ShootingRestOfClip,     Event.ClipEmpty,                        State.Searching),
    Transition_90(State.ShootingRestOfClip,     Event.VisionAssistButtonPressed,        State.Searching);

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

  }
}
