package engine.actions;

import engine.Action;

/**
 * Starts the next level of the current game.
 * 
 * @author Kyle Finke
 *
 */
public class NextLevelAction extends Action {

	@Override
	public void act() {
		getGameInfo().getTimelineManipulator().startNextLevel();
	}
}