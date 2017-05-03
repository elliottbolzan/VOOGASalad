package usecases;

import engine.actions.ActionInterface;
import engine.collisions.CollisionInterface;
import engine.entities.EntityInterface;
import engine.events.EventInterface;

/**
 * @author nikita This class contains the functions that get called when a
 *         collision between a character and a block occurs. The action of the
 *         character has been set to bounce back down, and the action of the
 *         block has been set to release a powerup.
 */
public class CharacterCollideWithBlock {
	public CharacterCollideWithBlock(EntityInterface character, EntityInterface block) {
		//GameInterface game = null;
		// collision has been detected already.
		CollisionInterface collision = null;
		// find the event that corresponds to this collision
		EventInterface characterEvent = null, blockEvent = null;
		characterEvent.act();
			//will call the action to act
			ActionInterface characterAction = null;
			characterAction.act();
			//will set the character speed and direction to opposite that which it was already going
		blockEvent.act();
			ActionInterface blockAction = null;
			blockAction.act();
			//will release a powerup.
				// create powerup
				//EntityInterface powerup = game.getPowerup();
				// set location and speed
				//powerup.setX(0);
				//powerup.setY(0);
				//powerup.setXSpeed(-1);
				// add it to frontend through NodeFactory.
	}
}
