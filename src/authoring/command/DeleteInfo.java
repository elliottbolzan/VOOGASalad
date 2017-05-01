package authoring.command;

/**
 * 
 * @author jimmy
 *
 */
public class DeleteInfo extends PositionInfo
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6162686959807670736L;

	public DeleteInfo(String entityName, double xPos, double yPos, int zPos, long entityId)
	{
		super(entityName, xPos, yPos, zPos, entityId);
	}

}
