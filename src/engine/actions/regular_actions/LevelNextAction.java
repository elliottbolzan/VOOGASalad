package engine.actions.regular_actions;

import engine.actions.Action;
import engine.game.gameloop.LevelStepStrategy;

/**
 * Starts the next level of the current game.
 * 
 * @author Kyle Finke, Matthew Barbano
 *
 */
public class LevelNextAction extends Action {

	@Override
	public void act() {
		if (!((LevelStepStrategy) getGameInfo().getLevelManager().getCurrentStepStrategy()).screenFinished()) {
			getGameInfo().getTimelineManipulator().startNextLevel();
		}
	}
}