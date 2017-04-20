package authoring.canvas;

import java.util.ArrayList;
import java.util.List;

import authoring.Workspace;
import utils.views.View;
import engine.Entity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
	private final int DEFAULT_WIDTH = 800;
	private final int DEFAULT_HEIGHT = 600;

	private Group gridNodes;
	private ScrollPane scrollScreen;
	private List<EntityView> entities;
	private Pane canvas;

	private double width;
	private double height;

	public Canvas(Workspace workspace) {
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
		canvas.setOnMouseClicked(eventHandler);
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
		canvas.setOnMouseDragged(eventHandler);
	}

	/**
	 * Set up the canvas (set all of its entities and displays to the default
	 * ones).
	 */
	private void setup()
	{
		gridNodes = new Group();
		// entityRegions = new HashMap<Node, Region>();
		entities = new ArrayList<EntityView>();
		scrollScreen = createScroller();
		this.setCenter(scrollScreen);

		scrollScreen.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>()
		{
			@Override
			public void handle(ScrollEvent event)
			{
				double zoomFactor = 1.05;
				double deltaY = event.getDeltaY();
				if (deltaY < 0) {
					zoomFactor = 2.0 - zoomFactor;
				}
				canvas.setScaleX(canvas.getScaleX() * zoomFactor);
				canvas.setScaleY(canvas.getScaleY() * zoomFactor);
				// canvas.setTranslateX(0 - (canvas.getScene().getWidth() *
				// canvas.getScaleX()) / 4);
				// canvas.setTranslateY(0 - (canvas.getScene().getHeight() *
				// canvas.getScaleY()) / 4);
				// canvas.setTranslateX(-1
				// * (scrollScreen.getViewportBounds().getWidth() -
				// (canvas.getScaleX() * canvas.getWidth())) / 2);
				// canvas.setTranslateY(
				// -1 * (scrollScreen.getViewportBounds().getHeight() -
				// (canvas.getScaleY() * canvas.getHeight()))
				// / 2);
				System.out.println(canvas.getScaleX());
				System.out.println(canvas.getScaleY());
				// scrollScreen.setHmax(canvas.getScaleX());
				// scrollScreen.setVmax(canvas.getScaleY());
				scrollScreen.setMaxHeight(canvas.getHeight());
				scrollScreen.setMaxWidth(canvas.getWidth());
				event.consume();
				updateDisplay();
			}
		});
	}

	/**
	 * Gets the current x value of the top-left corner.
	 * 
	 * @return x value of top-left corner of scroll panel.
	 */
	public double getXScrollAmount()
	{
		double viewPortX = scrollScreen.getViewportBounds().getWidth();
		return scrollScreen.getHvalue() * (width - viewPortX);
	}

	/**
	 * Gets the current y value of the top-left corner.
	 * 
	 * @return y value of top-left corner.
	 */
	public double getYScrollAmount()
	{
		double viewportY = scrollScreen.getViewportBounds().getHeight();
		return scrollScreen.getVvalue() * (height - viewportY);
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

	/**
	 * Creates the scroller for the canvas.
	 * 
	 * @return ScrollPane scroller for canvas
	 */
	private ScrollPane createScroller()
	{
		scrollScreen = new ScrollPane();
		canvas = new Pane();
		canvas.setPrefHeight(height);
		canvas.setPrefWidth(width);
		canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		canvas.getChildren().add(gridNodes);
		scrollScreen.setContent(canvas);
		// clickToAddEntity();
		updateDisplay();
		return scrollScreen;
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
		canvas.getChildren().add(newEntity);

		makeDraggable(newEntity);
		updateCanvasBounds();
		updateDisplay();
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
		canvas.getChildren().remove(entity);
	}

	/**
	 * Draw the grid for the Canvas
	 */
	private void drawGrid()
	{
		for (int i = 0; i < width / TILE_SIZE; i++) {
			for (int j = 0; j < height / TILE_SIZE; j++) {
				drawGridDot(i, j);
			}
		}
	}

	/**
	 * Draw a single grid dot at the given coordinates.
	 * 
	 * @param tileX
	 *            x coordinate of the grid dot
	 * @param tileY
	 *            y coordinate of the grid dot
	 */
	private void drawGridDot(double tileX, double tileY)
	{
		Circle gridMarker = new Circle();
		gridMarker.setCenterX(tileX * TILE_SIZE);
		gridMarker.setCenterY(tileY * TILE_SIZE);
		gridMarker.setRadius(1);
		gridMarker.setFill(Color.GREY);
		gridNodes.getChildren().add(gridMarker);
	}

	/**
	 * Bind the scrollbar of the scroll screen to the currently dragged object.
	 * As the object is dragged, the scrollbar will automatically re-adjust so
	 * that the object is in the center of the scroll screen. Moreover, this
	 * method updates the bounds of the canvas as the dragged object moves past
	 * the currently defined bounds.
	 * 
	 * @param entity
	 *            EntityView to bind the scrollbar to.
	 */
	private void makeDraggable(EntityView entity)
	{
		entity.translateXProperty().addListener(new ChangeListener<Number>()
		{

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldX, Number newX)
			{
				// scrollScreen.setHvalue(newX.doubleValue() / (width -
				// entity.getWidth()));
				// if (newX.intValue() < 0) {
				// entity.setTranslateX(0);
				// } else
				if (newX.intValue() + entity.getWidth() > width) {
					updateCanvasBounds();
				}
				updateDisplay();
			}

		});

		entity.translateYProperty().addListener(new ChangeListener<Number>()
		{

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldY, Number newY)
			{
				// scrollScreen.setVvalue(newY.doubleValue() / (height -
				// entity.getHeight()));
				// if (newY.intValue() < 0) {
				// entity.setTranslateY(0);
				// } else
				if (newY.intValue() + entity.getHeight() > height) {
					updateCanvasBounds();
				}
				updateDisplay();
			}

		});

		entity.minHeightProperty().addListener(e -> {
			updateDisplay();
		});
		entity.minWidthProperty().addListener(e -> {
			updateDisplay();
		});
	}

	/**
	 * This method draws the current bounds of the grid and updates the bounds
	 * of the layer.
	 */
	private void updateDisplay()
	{
		updateCanvasBounds();
		canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		gridNodes.getChildren().clear();
		drawGrid();
		canvas.setPrefHeight(height);
		canvas.setPrefWidth(width);
	}

	/**
	 * Updates the bounds of the canvas based on the position of the entity
	 * furthest from the origin in each direction (x and y)
	 */
	private void updateCanvasBounds()
	{
		double minX = 0;
		double minY = 0;
		double maxX = DEFAULT_WIDTH;
		double maxY = DEFAULT_HEIGHT;
		for (EntityView entity : entities) {
			double nodeMaxX = entity.getTranslateX() + entity.getBoundsInParent().getWidth();
			double nodeMaxY = entity.getTranslateY() + entity.getBoundsInParent().getHeight();
			double nodeMinX = entity.getTranslateX();
			double nodeMinY = entity.getTranslateY();
			if (nodeMaxX > maxX) {
				maxX = nodeMaxX;
			}
			if (nodeMaxY > maxY) {
				maxY = nodeMaxY;
			}
			if (nodeMinX < minX) {
				minX = nodeMinX;
			}
			if (nodeMinY < minY) {
				minY = nodeMinY;
			}
		}
		this.width = maxX - minX;
		this.height = maxY - minY;
		canvas.setTranslateX(minX / 2);
		canvas.setTranslateY(minY / 2);
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