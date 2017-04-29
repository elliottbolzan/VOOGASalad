package authoring.command;

/**
 * 
 * @author jimmy
 *
 */
public class PositionInfo extends SingleEntityCommandInfo
{

	private static final long serialVersionUID = 1199205996330909954L;
	private double xPos;
	private double yPos;

	public PositionInfo(String entityName, double xPos, double yPos, long entityId)
	{
		super(entityName, entityId);
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public double getX()
	{
		return xPos;
	}

	public double getY()
	{
		return yPos;
	}

}
