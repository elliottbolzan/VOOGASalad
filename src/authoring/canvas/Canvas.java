package authoring.canvas;

import java.util.ArrayList;
import java.util.List;

import authoring.Workspace;
import engine.Entity;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import utils.views.View;

/**
 * 
 * The Canvas is where you can drag Entities to create your own level. It can
 * contain multiple layers for backgrounds and foregrounds (layers). The layers
 * are mostly used to specify the order in which entities . The Canvas is
 * located in the center of the workspace. The top-left corner of the canvas is
 * set to be the origin (0,0) of the coordinate system.
 * 
 * @author jimmy
 *
 */
public class Canvas extends View
{
	private final int TILE_SIZE = 25;

	private List<EntityView> entities;

	private ZoomablePane zoomablePane;
	private ExpandablePane expandablePane;

	public Canvas(Workspace workspace)
	{
		super(workspace.getPolyglot().get("CanvasTitle"));
		setup();
	}

	/**
	 * Remove all of the entities from within the canvas.
	 */
	public void clear()
	{
		setup();
	}

	/**
	 * Set the given eventHandler to the onMouseClickedProperty of the pane
	 * portion of the canvas.
	 * 
	 * @param eventHandler
	 *            EventHandler that determines what happens when the mouse is
	 *            clicked on the pane of the Canvas.
	 */
	public void setPaneOnMouseClicked(EventHandler<? super MouseEvent> eventHandler)
	{
		expandablePane.setOnMouseClicked(eventHandler);
	}

	/**
	 * Set the given eventHandler to the onMouseDraggedProperty of the pane
	 * portion of the canvas.
	 * 
	 * @param eventHandler
	 *            EventHandler that determines what happens when the mouse is
	 *            dragged on the pane of the Canvas.
	 */
	public void setPaneOnMouseDragged(EventHandler<? super MouseEvent> eventHandler)
	{
		expandablePane.setOnMouseDragged(eventHandler);
	}

	/**
	 * Set up the canvas (set all of its entities and displays to the default
	 * ones).
	 */
	private void setup()
	{
		entities = new ArrayList<EntityView>();
		final Group group = new Group();
		expandablePane = new ExpandablePane();
		group.getChildren().add(expandablePane);
		zoomablePane = new ZoomablePane(group);

		VBox layout = new VBox();
		layout.getChildren().setAll(zoomablePane);

		VBox.setVgrow(zoomablePane, Priority.ALWAYS);

		this.setCenter(zoomablePane);
	}

	/**
	 * Gets the current x value of the top-left corner.
	 * 
	 * @return x value of top-left corner of scroll panel.
	 */
	public double getXScrollAmount()
	{
		// double viewPortX = scrollScreen.getViewportBounds().getWidth();
		// return scrollScreen.getHvalue() * (width - viewPortX);
		return 0;
	}

	/**
	 * Gets the current y value of the top-left corner.
	 * 
	 * @return y value of top-left corner.
	 */
	public double getYScrollAmount()
	{
		// double viewportY = scrollScreen.getViewportBounds().getHeight();
		// return scrollScreen.getVvalue() * (height - viewportY);
		return 0;
	}

	/**
	 * Returns the size of each tile in the Canvas.
	 * 
	 * @return canvas tile size.
	 */
	public double getTileSize()
	{
		return TILE_SIZE;
	}

	public List<EntityView> getSelectedEntities()
	{
		List<EntityView> selected = new ArrayList<EntityView>();
		entities.forEach(e -> {
			if (e.isSelected()) {
				selected.add(e);
			}
		});
		return selected;
	}

	/**
	 * Add an entity to the top-left corner of the canvas. This method makes a
	 * clone of the given Entity and creates an actual EntityView from it ((The
	 * EntityView is what is actually displayed in the Canvas.)
	 * 
	 * @param entity
	 *            Entity to be added to the canvas.
	 * @return EntityView that is displayed in the Canvas.
	 */
	public EntityView addEntity(Entity entity)
	{
		return this.addEntity(entity, 0, 0);
	}

	/**
	 * Add an entity to the given x and y position of the canvas. This method
	 * makes a clone of the given Entity and creates an actual EntityView from
	 * it (the EntityView is what is actually displayed in the Canvas)
	 * 
	 * @param entity
	 *            Entity to be added
	 * @param x
	 *            x Position
	 * @param y
	 *            y position
	 * @return EntityView that is displayed in the Canvas.
	 */
	public EntityView addEntity(Entity entity, double x, double y)
	{
		EntityView newEntity = new EntityView(entity, this, TILE_SIZE, x, y);
		Point2D tiledCoordinate = getTiledCoordinate(x, y);
		newEntity.setTranslateX(tiledCoordinate.getX());
		newEntity.setTranslateY(tiledCoordinate.getY());
		entities.add(newEntity);
		expandablePane.addEntity(newEntity, x, y);

		return newEntity;
	}

	/**
	 * Remove the given EntityView from the Canvas.
	 * 
	 * @param entity
	 *            EntityView to be removed from the Canvas.
	 */
	public void removeEntity(EntityView entity)
	{
		entities.remove(entity);
		expandablePane.getChildren().remove(entity);
	}

	/**
	 * Gets the tiled coordinate for the given x and y position. For example,
	 * for a grid tile_size of 25, position (19, 3) will map to (25, 0).
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return tiled coordinate of the given input.
	 */
	private Point2D getTiledCoordinate(double x, double y)
	{
		double gridX = ((int) x / TILE_SIZE) * TILE_SIZE;
		double gridY = ((int) y / TILE_SIZE) * TILE_SIZE;
		return new Point2D(gridX, gridY);
	}

}