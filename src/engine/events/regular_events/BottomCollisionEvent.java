package engine.events.regular_events;

import engine.Collision;
import engine.CollisionSide;

/**
 * Creates an Event that corresponds to two Entities colliding along the bottom
 * side of the first Entity entered.
 * 
 * @author Kyle Finke
 *
 */
public class BottomCollisionEvent extends CollisionEvent{
	public BottomCollisionEvent(){
		setCollision(new Collision(null, null, CollisionSide.BOTTOM));
	}	
}

