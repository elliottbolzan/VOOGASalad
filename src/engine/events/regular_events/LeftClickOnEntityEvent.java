package engine.events.regular_events;

import engine.events.Event;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;

/**
 * Responds to a left click of the mouse (press and release) on the Entity it is
 * attached to.
 * 
 * Source for getting Node's Bounds: http://stackoverflow.com/questions/31148690/get-real-position-of-a-node-in-javafx
 * 
 * @author Matthew Barbano
 *
 */
public class LeftClickOnEntityEvent extends Event {

	@Override
	public boolean act() {
		ClickHelper helper = new ClickHelper();
		return helper.mouseClickToProcess(this) && helper.buttonPressed(this, MouseButton.PRIMARY) && helper.withinBounds(this);
	}

	private boolean withinBounds() {
		Point2D clickedPoint = getGameInfo().getObservableBundle().getInputObservable().getLastPressedCoordinates();
		return clickedPoint.getX() > getEntity().getX()
				&& clickedPoint.getX() < (getEntity().getX() + getEntity().getWidth())
				&& clickedPoint.getY() > getEntity().getY()
				&& clickedPoint.getY() < (getEntity().getY() + getEntity().getHeight());
	}
}
