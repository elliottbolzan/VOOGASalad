package authoring.canvas;

import java.util.List;

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

	private boolean xResizeDragging;
	private boolean yResizeDragging;
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
	public static void makeDraggable(EntityView region, int gridSize)
	{
		final DragUtil resizer = new DragUtil(region, gridSize);

		region.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				resizer.mousePressedMove(event);
			}

		});

		region.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				resizer.mouseDraggedMove(event);
			}

		});
		region.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				resizer.mouseOverMove(event);
			}

		});
		region.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				resizer.mouseReleasedMove(event);
			}

		});
	}

	public static void makeResizeable(EntityView region, int gridSize)
	{
		final DragUtil resizer = new DragUtil(region, gridSize);

		region.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				resizer.mousePressedResize(event);
			}

		});

		region.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				resizer.mouseDraggedResize(event);
			}

		});
		region.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				resizer.mouseOverResize(event);
			}

		});
		region.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				resizer.mouseReleasedResize(event);
			}

		});
	}

	private void mouseReleasedMove(MouseEvent event)
	{
		moveDragging = false;
		region.setCursor(Cursor.DEFAULT);
	}

	private void mouseReleasedResize(MouseEvent event)
	{
		xResizeDragging = false;
		yResizeDragging = false;
		region.setCursor(Cursor.DEFAULT);
	}

	private void mouseOverMove(MouseEvent event)
	{
		if (isInMoveDraggableZone(event) && !moveDragging) {
			region.setCursor(Cursor.CLOSED_HAND);
		} else if (moveDragging) {
			region.setCursor(Cursor.NONE);
		}
	}

	private void mouseOverResize(MouseEvent event)
	{
		if ((isInXDraggableZone(event) && isInYDraggableZone(event)) || (xResizeDragging && yResizeDragging)) {
			region.setCursor(Cursor.SE_RESIZE);
		} else if (isInXDraggableZone(event) || xResizeDragging) {
			region.setCursor(Cursor.E_RESIZE);
		} else if (isInYDraggableZone(event) || yResizeDragging) {
			region.setCursor(Cursor.S_RESIZE);
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

	private void mouseDraggedMove(MouseEvent event)
	{
		if (moveDragging) {
			region.setCursor(Cursor.NONE);
			double mouseX = event.getX();
			double mouseY = event.getY();

			List<EntityView> movedEntities = region.getSelectedNeighbors();
			movedEntities.add(region);

			for (EntityView entity : movedEntities) {
				entity.setTranslateX(getTiledCoordinate(entity.getTranslateX() + mouseX - entity.getWidth() / 2));
				entity.setTranslateY(getTiledCoordinate(entity.getTranslateY() + mouseY - entity.getHeight() / 2));
			}
		}
	}

	private void mouseDraggedResize(MouseEvent event)
	{
		if (xResizeDragging && region.isSelected()) {

			double mousex = event.getX();

			double newWidth = untiledWidth + (mousex - x);
			untiledWidth = newWidth;

			if (untiledWidth >= tileSize) {
				region.setMinWidth(getTiledCoordinate(untiledWidth));
			}

			x = mousex;
		}

		if (yResizeDragging && region.isSelected()) {

			double mousey = event.getY();

			double newHeight = untiledHeight + (mousey - y);
			untiledHeight = newHeight;

			if (untiledHeight >= tileSize) {
				region.setMinHeight(getTiledCoordinate(untiledHeight));
			}

			y = mousey;
		}
	}

	private void mousePressedMove(MouseEvent event)
	{
		if (isInMoveDraggableZone(event)) {
			moveDragging = true;
			x = event.getX();
			y = event.getY();
		}
	}

	private void mousePressedResize(MouseEvent event)
	{

		if (isInXDraggableZone(event)) {
			xResizeDragging = true;

			if (!initMinWidth) {
				region.setMinWidth(region.getWidth());
				untiledWidth = region.getWidth();
				initMinWidth = true;
			}
			x = event.getX();
		}

		if (isInYDraggableZone(event)) {
			yResizeDragging = true;

			if (!initMinHeight) {
				region.setMinHeight(region.getHeight());
				untiledHeight = region.getHeight();
				initMinHeight = true;
			}
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