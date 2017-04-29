package authoring.command;

import authoring.canvas.EntityView;
import authoring.canvas.LayerEditor;

public class AddDeleteCommand extends EntityCommand
{

	private LayerEditor layer;
	private boolean add;

	public AddDeleteCommand(EntityView entityView, LayerEditor layer, boolean add)
	{
		super(entityView);
		this.layer = layer;
		this.add = add;
	}

	@Override
	public void execute()
	{
		addOrRemove(add);
	}

	@Override
	public void unexecute()
	{
		addOrRemove(!add);
	}

	private void addOrRemove(boolean add)
	{
		if (add) {
			layer.addEntity(super.getEntityView(), 1);
		} else {
			layer.getCanvas().removeEntity(super.getEntityView());
			layer.getLayers().forEach(e -> {
				if (e.getEntities().contains(super.getEntityView())) {
					e.getEntities().remove(super.getEntityView());
				}
			});
		}
	}
}
