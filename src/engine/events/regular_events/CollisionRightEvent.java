package engine.events.regular_events;

import engine.collisions.CollisionSide;

/**
 * Creates an Event that corresponds to two Entities colliding along the right
 * side of the first Entity entered.
 * 
 * @author Kyle Finke
 *
 */
public class CollisionRightEvent extends CollisionAllEvent {
	public CollisionRightEvent() {
		setCollisionSide(CollisionSide.RIGHT);
	}
}
