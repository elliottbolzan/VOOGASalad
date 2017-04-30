package engine.actions.regular_actions;

import engine.actions.Action;
import engine.game.gameloop.LevelStepStrategy;

/**
 * This action is used to reset the level or end the level if the Entity has no
 * lives remaining.
 * 
 * @author Matthew Barbano, Nikita
 *
 */
public class LevelLostAction extends Action {

	@Override
	public void act() {
		if (!((LevelStepStrategy) getGameInfo().getLevelManager().getCurrentStepStrategy()).screenFinished()) {
			getGameInfo().getScorebar().setLives(getGameInfo().getScorebar().getLives() - 1);
			getGameInfo().getTimelineManipulator().die(getGameInfo().getScorebar().getLives() <= 0);
		}
	}

}
