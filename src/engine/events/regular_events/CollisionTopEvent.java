package engine.events.regular_events;

import engine.collisions.CollisionSide;

/**
 * Creates an Event that corresponds to two Entities colliding along the top
 * side of the first Entity entered.
 * 
 * @author Kyle Finke
 *
 */
public class CollisionTopEvent extends CollisionAllEvent {
	public CollisionTopEvent() {
		setCollisionSide(CollisionSide.TOP);
	}
}
