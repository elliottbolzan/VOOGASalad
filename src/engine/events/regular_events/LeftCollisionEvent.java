package engine.events.regular_events;

import engine.Collision;
import engine.CollisionSide;

/**
 * Creates an Event that corresponds to two Entities colliding along the left
 * side of the first Entity entered.
 * 
 * @author Kyle Finke
 *
 */
public class LeftCollisionEvent extends CollisionEvent {

	public LeftCollisionEvent() {
		setCollision(new Collision(null, null, CollisionSide.LEFT));
	}
}
