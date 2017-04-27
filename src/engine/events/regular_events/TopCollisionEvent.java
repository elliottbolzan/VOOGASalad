package engine.events.regular_events;

import engine.CollisionSide;

/**
 * Creates an Event that corresponds to two Entities colliding along the top
 * side of the first Entity entered.
 * 
 * @author Kyle Finke
 *
 */
public class TopCollisionEvent extends CollisionEvent {
	public TopCollisionEvent() {
		setCollisionSide(CollisionSide.TOP);
	}
}
