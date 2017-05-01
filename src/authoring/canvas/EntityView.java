package authoring.canvas;

import java.util.List;
import java.util.Random;

import authoring.Workspace;
import engine.entities.Entity;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * An EntityView is the actual graphical display of an Entity in the Game
 * Authoring Environment. The EntityView consists of 5 things:<br>
 * 
 * 1. An ImageView which represents the Entity's image described by its
 * imagepath.<br>
 * 2. A border if the entity view is selected (define the region where a user
 * can drag to resize).<br>
 * 
 * If a mouse is dragged on the EntityView there are two possible responses:<br>
 * 1. If the mouse is dragged on the actual body of the image, then the
 * EntityView follows the mouse as it is dragged.<br>
 * 2. If the mouse is dragged on the border of the EntityView, then the
 * EntityView is resized accordingly.<br>
 * 
 * The ImageView is automatically resized to fit correctly in the grid with the
 * given grid tile size.
 * 
 * @author jimmy (modified by Elliott Bolzan)
 *
 */
public class EntityView extends VBox
{
	private final int MIN_SIZE = 10;

	private Entity entity;
	private ImageView image;
	private int tileSize;
	private boolean selected;
	private Canvas canvas;
	private long entityId;

	/**
	 * Create an EntityView with the given gridSize and (x,y) position. The
	 * Entity that is passed to the constructor is cloned (the EntityView
	 * doesn't keep track of the actual Entity that is passed).
	 * 
	 * @param entity
	 *            Entity to be represented by this EntityView
	 * @param gridSize
	 *            Grid tile size of the grid that the EntityView will be
	 *            represented in.
	 * @param x
	 *            initial x position of the EntityView
	 * @param y
	 *            initial y position of the EntityView
	 */
	public EntityView(Entity entity, Canvas canvas, int gridSize, double x, double y)
	{
		this(entity, new Random().nextLong(), canvas, gridSize, x, y);
	}

	public EntityView(Entity entity, long entityId, Canvas canvas, int gridSize, double x, double y)
	{
		this.entity = entity.clone();
		this.image = new ImageView(new Image(entity.getImagePath()));
		this.canvas = canvas;
		this.setMinHeight(entity.getHeight());
		this.setMinWidth(entity.getWidth());
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.tileSize = gridSize;
		selected = false;
		this.entityId = entityId;

		setup();
	}

	/**
	 * Setup the EntityView. This binds the entity's x, y, width, and height
	 * properties to this ImageView's respective properties. Moreover, this
	 * method makes the EntityView draggable/resizeable.
	 * 
	 * @param gridSize
	 *            Grid tile size
	 * @param x
	 *            initial x position of the EntityView
	 * @param y
	 *            initial y position of the EntityView
	 */
	private void setup()
	{
		setupBounds();
		setupEdit();

		this.getChildren().add(image);

		DragUtil.makeDraggable(this, tileSize);
		DragUtil.makeResizeable(this, tileSize);
	}

	private void setupEdit()
	{
		setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent mouseEvent)
			{
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						Entity defaultEntity = canvas.getWorkspace().getDefaults().getEntity(entity.getName());
						canvas.getWorkspace().getPanel().getEntityDisplay().editHelper(defaultEntity);
					}
				}
			}
		});
	}

	private void setupBounds()
	{
		this.setPrefHeight(MIN_SIZE);
		this.setPrefWidth(MIN_SIZE);

		image.fitWidthProperty().bind(this.minWidthProperty());
		image.fitHeightProperty().bind(this.minHeightProperty());

		entity.xProperty().bind(this.translateXProperty());
		entity.yProperty().bind(this.translateYProperty());
		entity.widthProperty().bind(image.fitWidthProperty());
		entity.heightProperty().bind(image.fitHeightProperty());

		this.setMinWidth(GridUtil.getTiledCoordinate(image.getBoundsInLocal().getWidth(), tileSize));
		this.setMinHeight(GridUtil.getTiledCoordinate(image.getBoundsInLocal().getHeight(), tileSize));
	}

	public void setEntity(Entity entity)
	{
		this.entity = entity.clone();
		image.setImage(new Image(entity.getImagePath()));
		this.setMinHeight(entity.getHeight());
		this.setMinWidth(entity.getWidth());
		setupBounds();
	}

	/**
	 * Returns the Entity that this EntityView is representing. The returned
	 * Entity's xProperty, yProperty, widthProperty, and heightProperty reflect
	 * the corresponding properties of the EntityView.
	 * 
	 * @return the Entity that this EntityView represents.
	 */
	public Entity getEntity()
	{
		return entity;
	}

	/**
	 * Set whether this EntityView is selected or not. If it is selected, then
	 * add a border and a DropShadow to this EntityView. Also, when the
	 * EntityView is selected, it can be resized.
	 * 
	 * @param selected
	 *            true if selected, false if not selected
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
		if (selected) {
			addBorder();
			this.setFocused(true);
		} else {
			this.setEffect(null);
			this.setBorder(null);
		}
	}

	private void addBorder()
	{
		Color borderColor = new Color(0, 0, 0, 0.2);
		this.setBorder(new Border(new BorderStroke(borderColor, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		DropShadow ds = new DropShadow();
		ds.setOffsetY(7.0);
		ds.setOffsetX(7.0);
		ds.setColor(Color.GRAY);
		this.setEffect(ds);
	}

	public long getEntityId()
	{
		return entityId;
	}

	/**
	 * Move by the given amount in the x direction
	 * 
	 * @param xAmount
	 *            amount to move in the x direction.
	 */
	public void moveX(double xAmount)
	{
		this.setTranslateX(this.getTranslateX() + xAmount);
	}

	/**
	 * Move by the given amount in the y direction.
	 * 
	 * @param yAmount
	 *            amount to move in the y direction.
	 */
	public void moveY(double yAmount)
	{
		this.setTranslateY(this.getTranslateY() + yAmount);
	}

	public void setX(double x)
	{
		this.setTranslateX(x);
	}

	public void setY(double y)
	{
		this.setTranslateY(y);
	}

	/**
	 * Gets every EntityView in the same Canvas that is also selected.
	 * 
	 * @return EntityViews in same canvas that are also selected.
	 */
	public List<EntityView> getSelectedNeighbors()
	{
		List<EntityView> selectedNeighbors = canvas.getSelectedEntities();
		if (selectedNeighbors.contains(this)) {
			selectedNeighbors.remove(this);
		}
		return selectedNeighbors;
	}

	/**
	 * Move by the given number of tiles in the x direction.
	 * 
	 * @param xGridAmount
	 *            number of grid tiles to shift by
	 */
	public void moveXGrid(double xGridAmount)
	{
		moveX(xGridAmount * tileSize);
	}

	/**
	 * Move by the given number of tiles in the y direction.
	 * 
	 * @param yGridAmount
	 *            number of grid tiles to shift by
	 */
	public void moveYGrid(double yGridAmount)
	{
		moveY(yGridAmount * tileSize);
	}

	/**
	 * Returns whether or not this EntityView is selected.
	 * 
	 * @return true if this entity is selected, false if not selected.
	 */
	public boolean isSelected()
	{
		return this.selected;
	}

	public Workspace getWorkspace()
	{
		return canvas.getWorkspace();
	}
}