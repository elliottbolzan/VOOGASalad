package engine.actions;

import engine.Action;
import engine.entities.CharacterEntity;
import engine.game.gameloop.LevelStepStrategy;

/**
 * Action for when a character dies. Can only be attached to a CharacterEntity; if attached to another type of 
 * Entity, will throw an exception.
 * @author Matthew Barbano
 *
 */
public class DieAction extends Action{
	
	public DieAction() {

	}

	@Override
	public void act() {
		CharacterEntity entity = (CharacterEntity) getEntity();  //TODO Throw exception here if not CharacterEntity
		entity.setLives(entity.getLives() - 1);
		System.out.println("Die action triggered"); 
		((LevelStepStrategy) getGameInfo().getCurrentStepStrategy()).endLevel(entity.getLives() <= 0);
		//TODO Throw exception here if current step strategy is not LevelStepStrategy (shouldn't ever be the case)
	}

}