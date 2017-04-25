package engine.events;

import engine.Event;

/**
 * Event that triggers attached Actions just once when the game starts up.
 * @author Matthew Barbano
 *
 */

public class GameStartupEvent extends Event {
	
	public GameStartupEvent(){
		
	}

	@Override
	public boolean act() {
		return getGameInfo().getEntitiesNeverUpdated();
	}
}
