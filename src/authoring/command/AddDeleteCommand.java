package authoring.command;

import authoring.canvas.EntityView;
import authoring.canvas.LayerEditor;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * 
 * @author jimmy
 *
 */
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
		EntityView entityView = super.getEntityView();
		if (add) {
			entityView.setOpacity(0);
			layer.addEntity(entityView, 1);
			animate(entityView, 0, 1);
			entityView.setSelected(true);
		} else {
			FadeTransition ft = animate(entityView, 1, 0);
			ft.setOnFinished(event -> {
				layer.getCanvas().removeEntity(entityView);
				layer.getLayers().forEach(e -> {
					if (e.getEntities().contains(entityView)) {
						e.getEntities().remove(entityView);
					}
				});
			});
		}
	}

	private FadeTransition animate(Node node, double from, double to)
	{
		FadeTransition ft = new FadeTransition(Duration.millis(300), node);
		ft.setFromValue(from);
		ft.setToValue(to);
		ft.play();
		return ft;
	}

}
