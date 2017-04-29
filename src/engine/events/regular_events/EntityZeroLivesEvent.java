package engine.events.regular_events;

import engine.events.Event;

public class EntityZeroLivesEvent extends Event {

	public EntityZeroLivesEvent() {
		
	}

	@Override
	public boolean act() {
		return getEntity().getLives() <= 0;
	}
}
