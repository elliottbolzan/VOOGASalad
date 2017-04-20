package authoring.canvas;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * 
 * @author jimmy
 *
 */
public class ExpandablePane extends Pane
{

	private final int TILE_SIZE = 25;
	private final int DEFAULT_WIDTH = 8000;
	private final int DEFAULT_HEIGHT = 6000;

	private Group gridNodes;

	private double width;
	private double height;

	public ExpandablePane()
	{
		super();
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
		this.setOnMouseClicked(eventHandler);
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
		this.setOnMouseDragged(eventHandler);
	}

	/**
	 * Set up the canvas (set all of its entities and displays to the default
	 * ones).
	 */
	private void setup()
	{
		gridNodes = new Group();
		this.getChildren().add(gridNodes);
		// entityRegions = new HashMap<Node, Region>();

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
	 * Add an entity to the top-left corner of the canvas. This method makes a
	 * clone of the given Entity and creates an actual EntityView from it ((The
	 * EntityView is what is actually displayed in the Canvas.)
	 * 
	 * @param entity
	 *            Entity to be added to the canvas.
	 * @return EntityView that is displayed in the Canvas.
	 */
	public void addNode(Node node)
	{
		this.addEntity(node, 0, 0);
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
	public void addEntity(Node node, double x, double y)
	{
		Point2D tiledCoordinate = getTiledCoordinate(x, y);
		node.setTranslateX(tiledCoordinate.getX());
		node.setTranslateY(tiledCoordinate.getY());
		this.getChildren().add(node);

		makeDraggable(node);
		updateCanvasBounds();
		updateDisplay();
	}

	/**
	 * Remove the given EntityView from the Canvas.
	 * 
	 * @param entity
	 *            EntityView to be removed from the Canvas.
	 */
	public void removeEntity(Node node)
	{
		this.getChildren().remove(node);
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
	private void makeDraggable(Node node)
	{
		node.translateXProperty().addListener(new ChangeListener<Number>()
		{

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldX, Number newX)
			{
				// scrollScreen.setHvalue(newX.doubleValue() / (width -
				// entity.getWidth()));
				// if (newX.intValue() < 0) {
				// node.setTranslateX(0);
				// } else
				if (newX.intValue() + node.getBoundsInLocal().getWidth() > width) {
					// gridNodes.getChildren().clear();
					// drawGrid();
					updateCanvasBounds();
				} else if (newX.intValue() < 0) {
					updateCanvasBounds();
				}
				updateDisplay();
			}

		});

		node.translateYProperty().addListener(new ChangeListener<Number>()
		{

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldY, Number newY)
			{
				// scrollScreen.setVvalue(newY.doubleValue() / (height -
				// entity.getHeight()));
				// if (newY.intValue() < 0) {
				// node.setTranslateY(0);
				// } else

				if (newY.intValue() + node.getBoundsInLocal().getHeight() > height) {
					// gridNodes.getChildren().clear();
					// drawGrid();
					updateCanvasBounds();
				}
				updateDisplay();
			}

		});

		// TODO: Fix height, width resizing updating display

		// node.minHeightProperty().addListener(e -> {
		// updateDisplay();
		// });
		// node.minWidthProperty().addListener(e -> {
		// updateDisplay();
		// });
	}

	/**
	 * This method draws the current bounds of the grid and updates the bounds
	 * of the layer.
	 */
	private void updateDisplay()
	{
		updateCanvasBounds();
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		// gridNodes.getChildren().clear();
		// drawGrid();
		this.setPrefHeight(height);
		this.setPrefWidth(width);
	}

	/**
	 * Updates the bounds of the canvas based on the position of the entity
	 * furthest from the origin in each direction (x and y)
	 */
	private void updateCanvasBounds()
	{
		double minX = 0;
		double minY = 0;
		double maxX = width;
		double maxY = height;
		for (Node node : this.getChildren()) {
			double nodeMaxX = node.getTranslateX() + node.getBoundsInParent().getWidth();
			double nodeMaxY = node.getTranslateY() + node.getBoundsInParent().getHeight();
			double nodeMinX = node.getTranslateX();
			double nodeMinY = node.getTranslateY();
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
			if (node.getTranslateX() < 0) {
				node.setTranslateX(0);
			}
			if (node.getTranslateY() < 0) {
				node.setTranslateY(0);
			}
		}
		this.width = maxX - minX;
		this.height = maxY - minY;
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