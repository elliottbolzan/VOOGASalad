package engine.actions;

import engine.Action;
import engine.game.gameloop.LevelStepStrategy;

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
			// This check added to fix bug of multiple NextLevelActions triggering
			if (!((LevelStepStrategy) getGameInfo().getLevelManager().getCurrentStepStrategy()).screenFinished()) {
				getGameInfo().getTimelineManipulator().startNextLevel();
			}
		} catch (ClassCastException e) {
			System.out.println("Casting error in NextLevelAction");
		}
	}
}