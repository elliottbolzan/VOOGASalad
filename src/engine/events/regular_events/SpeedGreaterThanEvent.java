package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;

/**
 * React to the entity having a speed greater than a given value
 * 
 * @author nikita
 */
public class SpeedGreaterThanEvent extends Event {

	public SpeedGreaterThanEvent() {
		addParam(new Parameter(getResource("TrackXSpeed"), boolean.class, false));
		addParam(new Parameter(getResource("TrackYSpeed"), boolean.class, false));
		addParam(new Parameter(getResource("Speed"), double.class, 0.0));
	}

	@Override
	public boolean act() {
		return ((boolean) getParam(getResource("TrackXSpeed"))
				&& getEntity().getXSpeed() > (double) getParam(getResource("Speed"))
				&& (boolean) getParam(getResource("TrackYSpeed"))
				&& getEntity().getYSpeed() > (double) getParam(getResource("Speed")))
				|| (!(boolean) getParam(getResource("TrackXSpeed")) && (boolean) getParam(getResource("TrackYSpeed"))
						&& getEntity().getYSpeed() > (double) getParam(getResource("Speed")))
				|| (!(boolean) getParam(getResource("TrackYSpeed")) && (boolean) getParam(getResource("TrackXSpeed"))
						&& getEntity().getXSpeed() > (double) getParam(getResource("Speed")));
	}

}
