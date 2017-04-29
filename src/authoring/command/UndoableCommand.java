package authoring.command;

/**
 * 
 * @author jimmy
 *
 */
public interface UndoableCommand
{
	public void execute();

	public void unexecute();
}
