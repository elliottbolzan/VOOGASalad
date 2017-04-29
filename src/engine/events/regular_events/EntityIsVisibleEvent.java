package engine.events.regular_events;

import engine.events.Event;

public class EntityIsVisibleEvent extends Event {

	public EntityIsVisibleEvent() {
		
	}

	@Override
	public boolean act() {
		return getEntity().getIsVisible();
	}

}
