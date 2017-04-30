package engine.events.regular_events;

import engine.events.ClickHelper;
import engine.events.Event;
import javafx.scene.input.MouseButton;

/**
 * Responds to a left click of the mouse (press and release) on the Entity it is
 * attached to.
 * 
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
}
