package authoring.command;

import authoring.canvas.EntityView;

/**
 * 
 * @author jimmy
 *
 */
public abstract class EntityCommand implements UndoableCommand
{

	private EntityView entityView;

	public EntityCommand(EntityView entityView)
	{
		this.entityView = entityView;
	}

	public EntityView getEntityView()
	{
		return entityView;
	}

}
