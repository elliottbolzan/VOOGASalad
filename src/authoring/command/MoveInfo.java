package authoring.command;

/**
 * 
 * @author jimmy
 *
 */
public class MoveInfo extends SingleEntityCommandInfo
{

	private static final long serialVersionUID = 1540267030725337292L;
	private double oldX;
	private double oldY;
	private double newX;
	private double newY;

	public MoveInfo(String entityName, long entityId, double oldX, double oldY, double newX, double newY)
	{
		super(entityName, entityId);
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = newX;
		this.newY = newY;
	}

	public double getOldX()
	{
		return oldX;
	}

	public double getOldY()
	{
		return oldY;
	}

	public double getNewX()
	{
		return newX;
	}

	public double getNewY()
	{
		return newY;
	}
}
