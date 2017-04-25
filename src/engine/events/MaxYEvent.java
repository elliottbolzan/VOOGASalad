package engine.events;

import engine.Event;
import engine.Parameter;

/**
 * Creates an Event that acts if the associated Entity goes above the given max
 * y value contained in the Event.
 * 
 * @author Kyle Finke
 *
 */
public class MaxYEvent extends Event {

	public MaxYEvent() {
		addParam(new Parameter("Max Y", Double.class, 0.0));
	}

	@Override
	public boolean act() {
		return getEntity().getY() >= (Double) getParam("Max Y");
	}

}
