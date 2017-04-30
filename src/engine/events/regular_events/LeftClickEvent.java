package engine.events.regular_events;

import engine.events.Event;
import javafx.scene.input.MouseButton;

/**
 * Responds to a left click (press and release) of the mouse.
 * @author Matthew Barbano
 *
 */
public class LeftClickEvent extends Event {
	
	@Override
	public boolean act() {
		return getGameInfo().getObservableBundle().getInputObservable().isMouseClickToProcess()
				&& getGameInfo().getObservableBundle().getInputObservable().getLastPressedMouseButton().equals(MouseButton.PRIMARY);
	}
	
}
