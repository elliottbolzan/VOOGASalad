package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;

/**
 * React to another event occurring, with a given delay.
 * 
 * @author nikita
 */
public class DelayEvent extends Event {
	private long startTime;

	public DelayEvent() {
		addParam(new Parameter(getResource("DelayAmount"), int.class, 0));
		addParam(new Parameter(getResource("EventId"), int.class, 0));
		startTime = -1;
	}

	@Override
	public boolean act() {
		if (getEntity().getEventById((int) getParam(getResource("EventId"))).isTriggered(true) && startTime == -1) {
			startTime = System.currentTimeMillis();
		}
		return startTime != -1 && startTime + (int) getParam(getResource("DelayAmount")) <= System.currentTimeMillis();
	}
}
