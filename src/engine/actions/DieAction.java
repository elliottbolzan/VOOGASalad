package engine.actions;

import engine.Action;
import engine.entities.CharacterEntity;
import engine.game.gameloop.LevelStepStrategy;

/**
 * Action for when a character dies. Can only be attached to a CharacterEntity;
 * if attached to another type of Entity, will throw an exception.
 * 
 * @author Matthew Barbano
 *
 */
public class DieAction extends Action {

	@Override
	public void act() {
		try {
			CharacterEntity entity = (CharacterEntity) getEntity();
			//This check added to fix bug of multiple DieActions triggering
			if (!((LevelStepStrategy) getGameInfo().getLevelManager().getCurrentStepStrategy()).screenFinished()) {
				entity.setLives(entity.getLives() - 1);
				getGameInfo().getTimelineManipulator().die(entity.getLives() <= 0);
			}
		} catch (ClassCastException e) {
			//throw new GameObjectException("CastingError");
			System.out.println("Casting error in DieAction");
		}
	}

}