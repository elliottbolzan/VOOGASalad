package engine.actions;

import engine.Action;
import engine.game.gameloop.LevelStepStrategy;

/**
 * Starts the next level of a game.
 * @author Kyle Finke
 *
 */
public class NextLevelAction extends Action {

	@Override
	public void act() {
		((LevelStepStrategy) getGameInfo().getCurrentStepStrategy()).startNextLevel();
	}

}