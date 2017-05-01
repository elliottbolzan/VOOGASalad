package engine.events.regular_events;

import engine.events.Event;

/**
 * Returns true if the Entity associated with this Event has 0 or fewer lives.
 * 
 * @author Kyle Finke
 *
 */
public class EntityZeroLivesEvent extends Event {

	@Override
	public boolean act() {
		return getEntity().getLives() <= 0;
	}
}
