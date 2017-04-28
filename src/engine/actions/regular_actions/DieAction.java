package engine.actions.regular_actions;

import engine.Action;
import engine.game.gameloop.LevelStepStrategy;
import exceptions.ObservableException;

/**
 * This action is used to reset the level or end the level if the Entity has no lives remaining.
 * @author Matthew Barbano, Nikita
 *
 */
public class DieAction extends Action {

	@Override
	public void act() {
		if (!((LevelStepStrategy) getGameInfo().getLevelManager().getCurrentStepStrategy()).screenFinished()) {
			getGameInfo().getTimelineManipulator().die(getEntity().getLives() <= 0);
		}
	}

}
