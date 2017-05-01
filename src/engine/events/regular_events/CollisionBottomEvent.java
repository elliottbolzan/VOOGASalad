package engine.events.regular_events;

import engine.collisions.CollisionSide;

/**
 * Creates an Event that corresponds to two Entities colliding along the bottom
 * side of the first Entity entered.
 * 
 * @author Kyle Finke
 *
 */
public class CollisionBottomEvent extends CollisionAllEvent {
	public CollisionBottomEvent() {
		setCollisionSide(CollisionSide.BOTTOM);
	}
}
