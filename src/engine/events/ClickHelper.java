package engine.events;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;

/**
 * A helper class for the Events related to clicking.
 * 
 * * Source for getting Node's Bounds:
 * http://stackoverflow.com/questions/31148690/get-real-position-of-a-node-in-javafx
 * 
 * @author Matthew Barbano
 *
 */
public final class ClickHelper {

	public boolean mouseClickToProcess(Event event) {
		return event.getGameInfo().getObservableBundle().getInputObservable().isMouseClickToProcess();
	}

	public boolean buttonPressed(Event event, MouseButton button) {
		return event.getGameInfo().getObservableBundle().getInputObservable().getLastPressedMouseButton()
				.equals(button);
	}

	public boolean withinBounds(Event event) {
		Point2D clickedPoint = event.getGameInfo().getObservableBundle().getInputObservable()
				.getLastPressedCoordinates();
		return clickedPoint.getX() > event.getEntity().getX()
				&& clickedPoint.getX() < (event.getEntity().getX() + event.getEntity().getWidth())
				&& clickedPoint.getY() > event.getEntity().getY()
				&& clickedPoint.getY() < (event.getEntity().getY() + event.getEntity().getHeight());
	}
}
