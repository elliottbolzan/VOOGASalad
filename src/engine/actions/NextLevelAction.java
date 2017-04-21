package engine.actions;

import engine.Action;
import engine.game.gameloop.LevelStepStrategy;
import exceptions.GameObjectException;

/**
 * Starts the next level of the current game.
 * 
 * @author Kyle Finke
 *
 */
public class NextLevelAction extends Action {

	@Override
	public void act() {
		try {
			// This check added to fix bug of multiple DieActions triggering
			if (!((LevelStepStrategy) getGameInfo().getLevelManager().getCurrentStepStrategy()).screenFinished()) {
				getGameInfo().getTimelineManipulator().startNextLevel();
			}
		} catch (ClassCastException e) {
			throw new GameObjectException("CastingError");
		}
	}
}