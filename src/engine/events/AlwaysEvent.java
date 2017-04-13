package engine.events;

import engine.Event;

/**
 * @author nikita An event that is triggered on every single step of the game
 *         loop
 */
public class AlwaysEvent extends Event {

	@Override
	public boolean act() {
		return true;
	}

}
