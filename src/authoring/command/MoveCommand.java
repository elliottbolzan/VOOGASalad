package authoring.command;

import authoring.canvas.EntityView;

public class MoveCommand extends EntityCommand
{

	double shiftX;
	double shiftY;

	public MoveCommand(EntityView entityView, MoveInfo moveInfo)
	{
		super(entityView);
		this.shiftX = moveInfo.getShiftX();
		this.shiftY = moveInfo.getShiftY();
	}

	@Override
	public void execute()
	{
		super.getEntityView().setTranslateX(super.getEntityView().getTranslateX() + shiftX);
		super.getEntityView().setTranslateY(super.getEntityView().getTranslateY() + shiftY);
	}

	@Override
	public void unexecute()
	{
		super.getEntityView().setTranslateX(super.getEntityView().getTranslateX() - shiftX);
		super.getEntityView().setTranslateY(super.getEntityView().getTranslateY() - shiftY);
	}

}
