package authoring.command;

import authoring.canvas.EntityView;

public class ResizeCommand extends EntityCommand
{

	double oldHeight;
	double oldWidth;
	double newHeight;
	double newWidth;
	double oldX;
	double oldY;
	double newX;
	double newY;

	public ResizeCommand(EntityView entityView, ResizeInfo resizeInfo)
	{
		super(entityView);
		this.oldHeight = resizeInfo.getOldHeight();
		this.oldWidth = resizeInfo.getOldWidth();
		this.newHeight = resizeInfo.getNewHeight();
		this.newWidth = resizeInfo.getNewWidth();
		this.oldX = resizeInfo.getOldX();
		this.oldY = resizeInfo.getOldY();
		this.newX = resizeInfo.getNewX();
		this.newY = resizeInfo.getNewY();
	}

	@Override
	public void execute()
	{
		super.getEntityView().setTranslateX(newX);
		super.getEntityView().setTranslateY(newY);
		super.getEntityView().setMinHeight(newHeight);
		super.getEntityView().setMinWidth(newWidth);
	}

	@Override
	public void unexecute()
	{
		super.getEntityView().setTranslateX(oldX);
		super.getEntityView().setTranslateY(oldY);
		super.getEntityView().setMinHeight(oldHeight);
		super.getEntityView().setMinWidth(oldWidth);
	}

}
