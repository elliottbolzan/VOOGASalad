package engine.events.regular_events;

import engine.Collision;
import engine.CollisionSide;

/**
 * Creates an Event that corresponds to two Entities colliding along the right
 * side of the first Entity entered.
 * 
 * @author Kyle Finke
 *
 */
public class RightCollisionEvent extends CollisionEvent {
	public RightCollisionEvent() {
		setCollision(new Collision(null, null, CollisionSide.RIGHT));
	}
}
