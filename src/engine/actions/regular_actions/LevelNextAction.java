package engine.actions.regular_actions;

import engine.actions.Action;
import engine.game.gameloop.LevelStepStrategy;

/**
 * Starts the next level of the current game.
 * 
 * @author Kyle Finke
 *
 */
public class LevelNextAction extends Action {

	@Override
	public void act() {
		try {
			if (!((LevelStepStrategy) getGameInfo().getLevelManager().getCurrentStepStrategy()).screenFinished()) {
				getGameInfo().getTimelineManipulator().startNextLevel();
			}
		} catch (ClassCastException e) {
			System.out.println("Casting error in NextLevelAction");
		}
	}
}