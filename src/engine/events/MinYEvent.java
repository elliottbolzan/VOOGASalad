package engine.events;

import engine.Event;
import engine.Parameter;

/**
 * Creates an Event that acts if the associated Entity goes below the given min
 * y value contained in the Event.
 * 
 * @author Kyle Finke
 *
 */
public class MinYEvent extends Event {

	public MinYEvent() {
		addParam(new Parameter("Min Y", Double.class, Double.MIN_VALUE));
	}

	@Override
	public boolean act() {
		return getEntity().getY() <= (Double) getParam("Min Y");
	}

}
