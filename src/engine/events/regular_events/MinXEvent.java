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
public class MinXEvent extends Event {

	public MinXEvent() {
		addParam(new Parameter("Min X", Double.class, 0.0));
	}

	@Override
	public boolean act() {
		return getEntity().getX() <= (Double) getParam("Min X");
	}

}
