package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;

/**
 * React to entity having zero acceleration
 * 
 * @author nikita
 */
public class ZeroAccelerationEvent extends Event {

	public ZeroAccelerationEvent() {
		addParam(new Parameter(getResource("TrackXAcceleration"), boolean.class, false));
		addParam(new Parameter(getResource("TrackYAcceleration"), boolean.class, false));
	}

	@Override
	public boolean act() {
		return ((boolean) getParam(getResource("TrackXAcceleration")) && getEntity().getXAcceleration() == 0
				&& (boolean) getParam(getResource("TrackYAcceleration")) && getEntity().getYAcceleration() == 0)
				|| (!(boolean) getParam(getResource("TrackXAcceleration"))
						&& (boolean) getParam(getResource("TrackYAcceleration")) && getEntity().getYAcceleration() == 0)
				|| (!(boolean) getParam(getResource("TrackYAcceleration"))
						&& (boolean) getParam(getResource("TrackXAcceleration"))
						&& getEntity().getXAcceleration() == 0);
	}
}
