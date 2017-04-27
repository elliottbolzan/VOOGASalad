package authoring.command;

import authoring.canvas.EntityView;
import authoring.canvas.LayerEditor;

public class AddCommand extends EntityCommand
{

	private LayerEditor layer;

	public AddCommand(EntityView entityView, LayerEditor layer)
	{
		super(entityView);
		this.layer = layer;
	}

	@Override
	public void execute()
	{
		layer.getChildren().add(super.getEntityView());
	}

	@Override
	public void unexecute()
	{
		layer.getChildren().remove(super.getEntityView());
	}

}
