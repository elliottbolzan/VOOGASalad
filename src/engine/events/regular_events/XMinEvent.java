package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;

/**
 * Creates an Event that acts if the associated Entity goes below the given min
 * x value contained in the Event.
 * 
 * @author Kyle Finke
 *
 */
public class XMinEvent extends Event {

	public XMinEvent() {
		addParam(new Parameter(getResource("MinimumX"), Double.class, 0.0));
	}

	@Override
	public boolean act() {
		return getEntity().getX() <= (Double) getParam(getResource("MinimumX"));
	}

}
