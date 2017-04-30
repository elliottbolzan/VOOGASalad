package authoring.command;

import java.util.Random;

/**
 * 
 * @author jimmy
 *
 */
public class AddInfo extends PositionInfo
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3622141588281702380L;

	public AddInfo(String entityName, double xPos, double yPos, int zPos)
	{
		super(entityName, xPos, yPos, zPos, new Random().nextLong());
	}

}
