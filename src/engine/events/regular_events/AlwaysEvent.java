package engine.events.regular_events;

import engine.Event;

/**
 * An event that is triggered on every single step of the game loop.
 * 
 * @author nikita
 */
public class AlwaysEvent extends Event {

	@Override
	public boolean act() {
		return true;
	}

}
