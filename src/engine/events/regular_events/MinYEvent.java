package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;

/**
 * Creates an Event that acts if the associated Entity goes below the given min
 * y value contained in the Event.
 * 
 * @author Kyle Finke
 *
 */
public class MinYEvent extends Event {

	public MinYEvent() {
		addParam(new Parameter("Min Y", Double.class, 0.0));
	}

	@Override
	public boolean act() {
		return getEntity().getY() <= (Double) getParam("Min Y");
	}

}
