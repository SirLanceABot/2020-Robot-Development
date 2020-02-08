package frc.autonomous.commands;

public interface Command
{
    void init();
    void execute();
    boolean isFinished();
    void end();
}