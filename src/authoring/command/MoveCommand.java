package authoring.command;

import authoring.canvas.EntityView;

public class MoveCommand extends EntityCommand
{

	double oldX;
	double oldY;
	double newX;
	double newY;

	public MoveCommand(EntityView entityView, MoveInfo moveInfo)
	{
		super(entityView);
		this.oldX = moveInfo.getOldX();
		this.oldY = moveInfo.getOldY();
		this.newX = moveInfo.getNewX();
		this.newY = moveInfo.getNewY();
	}

	@Override
	public void execute()
	{
		super.getEntityView().setTranslateX(newX);
		super.getEntityView().setTranslateY(newY);
	}

	@Override
	public void unexecute()
	{
		super.getEntityView().setTranslateX(oldX);
		super.getEntityView().setTranslateY(oldY);
	}

}
