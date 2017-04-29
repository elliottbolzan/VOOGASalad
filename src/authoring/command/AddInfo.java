package authoring.command;

import java.util.Random;

public class AddInfo extends PositionInfo
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3622141588281702380L;

	public AddInfo(String entityName, double xPos, double yPos)
	{
		super(entityName, xPos, yPos, new Random().nextLong());
	}

}
