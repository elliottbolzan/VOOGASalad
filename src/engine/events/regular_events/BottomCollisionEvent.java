package engine.events.regular_events;

import engine.collisions.CollisionSide;

/**
 * Creates an Event that corresponds to two Entities colliding along the bottom
 * side of the first Entity entered.
 * 
 * @author Kyle Finke
 *
 */
public class BottomCollisionEvent extends CollisionEvent{
	public BottomCollisionEvent(){
		setCollisionSide(CollisionSide.BOTTOM);
	}	
}

