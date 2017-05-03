package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;

/**
 * React to entity having zero speed
 * 
 * @author nikita
 */
public class ZeroSpeedEvent extends Event {

	public ZeroSpeedEvent() {
		addParam(new Parameter(getResource("TrackXSpeed"), boolean.class, false));
		addParam(new Parameter(getResource("TrackYSpeed"), boolean.class, false));
	}

	@Override
	public boolean act() {
		return ((boolean) getParam(getResource("TrackXSpeed")) && getEntity().getXSpeed() == 0
				&& (boolean) getParam(getResource("TrackYSpeed")) && getEntity().getYSpeed() == 0)
				|| (!(boolean) getParam(getResource("TrackXSpeed")) && (boolean) getParam(getResource("TrackYSpeed"))
						&& getEntity().getYSpeed() == 0)
				|| (!(boolean) getParam(getResource("TrackYSpeed")) && (boolean) getParam(getResource("TrackXSpeed"))
						&& getEntity().getXSpeed() == 0);
	}
}
