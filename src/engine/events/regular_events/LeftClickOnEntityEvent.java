package engine.events.regular_events;

import engine.Parameter;
import engine.entities.Entity;
import engine.events.Event;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

/**
 * Responds to a left click of the mouse (press and release) on the Entity it is
 * attached to.
 * 
 * @author Matthew Barbano
 *
 */
public class LeftClickOnEntityEvent extends Event {

	@Override
	public boolean act() {
		return getGameInfo().getObservableBundle().getInputObservable().isMouseClickToProcess() && withinBounds();
	}

	private boolean withinBounds() {
		Point2D clickedPoint = getGameInfo().getObservableBundle().getInputObservable().getLastPressedCoordinates();
		return clickedPoint.getX() > getEntity().getX()
				&& clickedPoint.getX() < getEntity().getX() + getEntity().getWidth()
				&& clickedPoint.getY() > getEntity().getY()
				&& clickedPoint.getY() < getEntity().getY() + getEntity().getHeight();
	}
}
