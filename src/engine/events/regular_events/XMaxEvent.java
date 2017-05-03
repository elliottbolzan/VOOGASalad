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
public class XMaxEvent extends Event {

	public XMaxEvent() {
		addParam(new Parameter(getResource("MaximumX"), Double.class, 0.0));
	}

	@Override
	public boolean act() {
		return getEntity().getX() >= (Double) getParam(getResource("MaximumX"));
	}
}
