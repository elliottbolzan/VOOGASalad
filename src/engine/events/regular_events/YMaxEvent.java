package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;

/**
 * Creates an Event that acts if the associated Entity goes above the given max
 * y value contained in the Event.
 * 
 * @author Kyle Finke
 *
 */
public class YMaxEvent extends Event {

	public YMaxEvent() {
		addParam(new Parameter("Max Y", Double.class, 0.0));
	}

	@Override
	public boolean act() {
		return getEntity().getY() >= (Double) getParam("Max Y");
	}

}
