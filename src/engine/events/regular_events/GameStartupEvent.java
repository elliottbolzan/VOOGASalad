package engine.events.regular_events;

import engine.events.Event;

/**
 * Event that triggers attached Actions just once when the game starts up.
 * 
 * @author Matthew Barbano
 *
 */

public class GameStartupEvent extends Event {

	@Override
	public boolean act() {
		return getGameInfo().getEntitiesNeverUpdated();
	}
}
