package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;

/**
 * This event detects if the Entity it is attached to is within the rectangular
 * region specified by the parameters MinX, MaxX, MinY, MaxY. Note these
 * parameters are based off of a coordinate system with the positive y axis
 * pointing downwards (and positive x pointing left, as normal).
 * 
 * One example use of this event is to make the hero die if it falls down an
 * "endless pit". Simply attach this InRegionEvent to the main hero Entity, then
 * attach a DieAction to this InRegionEvent. Specify parameters MinX =
 * Integer.MIN_VALUE, MaxX = Integer.MAX_VALUE, MinY = Bottom of Screen, MaxY =
 * Integer.MAX_VALUE.
 * 
 * @author Matthew Barbano
 *
 */
public class InsideRegionEvent extends Event {

	public InsideRegionEvent() {
		addParam(new Parameter("Minimum X", Double.class, 0.0));
		addParam(new Parameter("Maximum X", Double.class, 0.0));
		addParam(new Parameter("Minimum Y", Double.class, 0.0));
		addParam(new Parameter("Maximum Y", Double.class, 0.0));
	}

	@Override
	public boolean act() {
		return getEntity().getX() > ((Double) getParam("MinX")) && getEntity().getX() < ((Double) getParam("MaxX"))
				&& getEntity().getY() > ((Double) getParam("MinY")) && getEntity().getY() < ((Double) getParam("MaxY"));
	}
}
