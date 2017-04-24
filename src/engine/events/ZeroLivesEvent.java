package engine.events;

import engine.Event;

public class ZeroLivesEvent extends Event{

	@Override
	public boolean act() {
		return getEntity().getLives() == 0;
	}
}
