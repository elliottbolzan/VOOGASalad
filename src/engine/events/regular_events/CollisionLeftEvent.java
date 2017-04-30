package engine.events.regular_events;

import engine.collisions.CollisionSide;

/**
 * Creates an Event that corresponds to two Entities colliding along the left
 * side of the first Entity entered.
 * 
 * @author Kyle Finke
 *
 */
public class CollisionLeftEvent extends CollisionAllEvent {

	public CollisionLeftEvent() {
		setCollisionSide(CollisionSide.LEFT);
	}
}
