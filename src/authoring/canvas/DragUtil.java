package authoring.canvas;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

/**
 * The DragUtil class allows you to make an entity moveable and resizeable when
 * the mouse is dragged on top of it.
 * 
 * Some code borrowed from
 * http://stackoverflow.com/questions/16925612/how-to-resize-component-with-mouse-drag-in-javafx
 * 
 * @author jimmy
 *
 */
public class DragUtil
{

	/**
	 * The margin around the control that a user can click in to start resizing
	 * the region.
	 */
	private static final int RESIZE_MARGIN = 5;

	private final EntityView region;

	private int tileSize;

	private double x;
	private double y;

	private double untiledHeight;
	private double untiledWidth;

	private boolean initMinWidth;
	private boolean initMinHeight;

	private boolean xDragging;
	private boolean yDragging;
	private boolean moveDragging;

	private DragUtil(EntityView entityDisplay, int gridSize)
	{
		region = entityDisplay;
		tileSize = gridSize;
	}

	/**
	 * Makes the given EntityView moveable and resizeable on mouse drag. When
	 * the mouse is dragged on the rectangular border of the EntityView, the
	 * EntityView is resized. When the mouse is dragged on the center of the
	 * EntityView, the EntityView is translated (moved) to be on top of the
	 * mouse. The translation/resizing is done such that the EntityView snaps to
	 * the grid whose tile size is defined as gridSize.
	 * 
	 * @param region
	 *            EntityView to be made draggable
	 * @param gridSize
	 *            the tile size of the grid that the EntityView is in.
	 */
	public static void makeDraggableResizable(EntityView region, int gridSize)
	{
		final DragUtil resizer = new DragUtil(region, gridSize);

		region.setOnMousePressed(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				resizer.mousePressed(event);
			}
		});
		region.setOnMouseDragged(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				resizer.mouseDragged(event);
			}
		});
		region.setOnMouseMoved(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				resizer.mouseOver(event);
			}
		});
		region.setOnMouseReleased(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				resizer.mouseReleased(event);
			}
		});
	}

	private void mouseReleased(MouseEvent event)
	{
		xDragging = false;
		yDragging = false;
		moveDragging = false;
		region.setCursor(Cursor.DEFAULT);
	}

	private void mouseOver(MouseEvent event)
	{
		if ((isInXDraggableZone(event) && isInYDraggableZone(event)) || (xDragging && yDragging)) {
			region.setCursor(Cursor.SE_RESIZE);
		} else if (isInXDraggableZone(event) || xDragging) {
			region.setCursor(Cursor.E_RESIZE);
		} else if (isInYDraggableZone(event) || yDragging) {
			region.setCursor(Cursor.S_RESIZE);
		} else if (isInMoveDraggableZone(event) && !moveDragging) {
			region.setCursor(Cursor.CLOSED_HAND);
		} else if (moveDragging) {
			region.setCursor(Cursor.NONE);
		} else {
			region.setCursor(Cursor.DEFAULT);
		}
	}

	private boolean isInXDraggableZone(MouseEvent event)
	{
		return event.getX() > (region.getWidth() - RESIZE_MARGIN) && region.isSelected();
	}

	private boolean isInYDraggableZone(MouseEvent event)
	{
		return event.getY() > (region.getHeight() - RESIZE_MARGIN) && region.isSelected();
	}

	private boolean isInMoveDraggableZone(MouseEvent event)
	{
		return event.getX() > 0 && event.getX() < region.getWidth() - RESIZE_MARGIN && event.getY() > 0
				&& event.getY() < region.getHeight() - RESIZE_MARGIN;
	}

	private void mouseDragged(MouseEvent event)
	{
		if (xDragging && region.isSelected()) {

			double mousex = event.getX();

			double newWidth = untiledWidth + (mousex - x);
			untiledWidth = newWidth;

			if (untiledWidth >= tileSize) {
				region.setMinWidth(getTiledCoordinate(untiledWidth));
			}

			x = mousex;
		}

		if (yDragging && region.isSelected()) {

			double mousey = event.getY();

			double newHeight = untiledHeight + (mousey - y);
			untiledHeight = newHeight;

			if (untiledHeight >= tileSize) {
				region.setMinHeight(getTiledCoordinate(untiledHeight));
			}

			y = mousey;
		}

		if (moveDragging) {
			region.setCursor(Cursor.NONE);
			double mouseX = event.getX();
			double mouseY = event.getY();

			region.setTranslateX(getTiledCoordinate(region.getTranslateX() + mouseX - region.getWidth() / 2));
			region.setTranslateY(getTiledCoordinate(region.getTranslateY() + mouseY - region.getHeight() / 2));
		}
	}

	private void mousePressed(MouseEvent event)
	{

		if (isInXDraggableZone(event)) {
			xDragging = true;

			if (!initMinWidth) {
				region.setMinWidth(region.getWidth());
				untiledWidth = region.getWidth();
				initMinWidth = true;
			}
			x = event.getX();
		}

		if (isInYDraggableZone(event)) {
			yDragging = true;

			if (!initMinHeight) {
				region.setMinHeight(region.getHeight());
				untiledHeight = region.getHeight();
				initMinHeight = true;
			}
			y = event.getY();
		}

		if (isInMoveDraggableZone(event)) {
			moveDragging = true;
			x = event.getX();
			y = event.getY();
		}
	}

	private double getTiledCoordinate(double coordinate)
	{
		double gridCoordinate = ((int) coordinate / tileSize) * tileSize;
		if (coordinate % tileSize > tileSize / 2) {
			return gridCoordinate + tileSize;
		}
		return gridCoordinate;
	}
}