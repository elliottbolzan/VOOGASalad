package engine.actions.regular_actions;

import engine.actions.Action;

/**
 * Action for when a character dies. Can only be attached to a CharacterEntity;
 * if attached to another type of Entity, will throw an exception.
 * 
 * @author Matthew Barbano
 *
 */
public class LivesDecrementAction extends Action {

	@Override
	public void act() {
		getEntity().setLives(getEntity().getLives() - 1);
	}

}