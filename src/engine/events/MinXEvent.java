package engine.events;

import engine.Event;
import engine.Parameter;

/**
 * Creates an Event that acts if the associated Entity goes below the given min
 * x value contained in the Event.
 * 
 * @author Kyle Finke
 *
 */
public class MinXEvent extends Event {

	public MinXEvent() {
		addParam(new Parameter("Min X", Double.class, Double.MIN_VALUE));
	}

	@Override
	public boolean act() {
		return getEntity().getX() <= (Double) getParam("Min X");
	}

}
