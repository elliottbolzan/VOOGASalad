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
public class YMinEvent extends Event {

	public YMinEvent() {
		addParam(new Parameter(getResource("MinimumY"), Double.class, 0.0));
	}

	@Override
	public boolean act() {
		return getEntity().getY() <= (Double) getParam(getResource("MinimumY"));
	}

}
