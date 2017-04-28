package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;

/**
 * Creates an Event that acts if the associated Entity goes above the given max
 * x value contained in the Event.
 * 
 * @author Kyle Finke
 *
 */
public class MaxXEvent extends Event {

	public MaxXEvent() {
		addParam(new Parameter("Max X", Double.class, 0.0));
	}

	@Override
	public boolean act() {
		return getEntity().getX() >= (Double) getParam("Max X");
	}

}
