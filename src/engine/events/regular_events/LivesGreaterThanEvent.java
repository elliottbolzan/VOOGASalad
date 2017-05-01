package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;

/**
 * React to lives being greater than a certain amount
 * 
 * @author nikita
 */
public class LivesGreaterThanEvent extends Event {

	public LivesGreaterThanEvent() {
		addParam(new Parameter("Amount of Lives", int.class, 0));
	}

	@Override
	public boolean act() {
		return Integer.parseInt(getGameInfo().getScorebar().getScore()) > (int) getParam("Amount of Lives");
	}
}
