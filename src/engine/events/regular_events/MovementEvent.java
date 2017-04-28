package engine.events.regular_events;

import engine.events.Event;

/**
 * Event that responds to movement of the entity.
 * 
 * @author nikita
 */
public class MovementEvent extends Event {
	private double previousX, previousY;
	private boolean firstTime = true;

	@Override
	public boolean act() {
		boolean ret = previousX != getEntity().getX() || previousY != getEntity().getY();
		previousX = getEntity().getX();
		previousY = getEntity().getY();
		if (firstTime) {
			firstTime = false;
			return false;
		}
		return ret;
	}

}
