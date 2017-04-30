package engine.events.regular_events;

import engine.GameInfo;
import engine.events.Event;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;

/**
 * A helper class for the Events related to clicking.
 * 
 * @author Matthew Barbano
 *
 */
public final class ClickHelper {
	
	public boolean mouseClickToProcess(Event event){
		return event.getGameInfo().getObservableBundle().getInputObservable().isMouseClickToProcess();
	}
	
	public boolean buttonPressed(Event event, MouseButton button){
		return event.getGameInfo().getObservableBundle().getInputObservable().getLastPressedMouseButton().equals(button);
	}
	
	public boolean withinBounds(Event event){
		Point2D clickedPoint = event.getGameInfo().getObservableBundle().getInputObservable().getLastPressedCoordinates();
		return clickedPoint.getX() > event.getEntity().getX()
				&& clickedPoint.getX() < (event.getEntity().getX() + event.getEntity().getWidth())
				&& clickedPoint.getY() > event.getEntity().getY()
				&& clickedPoint.getY() < (event.getEntity().getY() + event.getEntity().getHeight());
	}
}
