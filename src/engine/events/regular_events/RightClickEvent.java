package engine.events.regular_events;

import engine.events.ClickHelper;
import engine.events.Event;
import javafx.scene.input.MouseButton;

/**
 * Responds to a right click (press and release) of the mouse.
 * @author Matthew Barbano
 *
 */
public class RightClickEvent extends Event {
	
	@Override
	public boolean act() {
		ClickHelper helper = new ClickHelper();
		return helper.mouseClickToProcess(this) && helper.buttonPressed(this, MouseButton.SECONDARY);
	}
	
}
