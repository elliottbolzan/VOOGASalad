package authoring.command;

public interface UndoableCommand
{
	public void execute();

	public void unexecute();
}
