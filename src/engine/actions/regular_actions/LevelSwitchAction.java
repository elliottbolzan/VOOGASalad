package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;
import engine.game.gameloop.LevelStepStrategy;

/**
 * Terminates the current level and starts any level of the game, given by the
 * parameter.
 * 
 * @author Matthew Barbano
 *
 */
public class LevelSwitchAction extends Action {

	public LevelSwitchAction() {
		addParam(new Parameter(getResource("NewLevel"), Integer.class, 0));
	}

	@Override
	public void act() {
		if (!((LevelStepStrategy) getGameInfo().getLevelManager().getCurrentStepStrategy()).screenFinished()) {
			getGameInfo().getTimelineManipulator().startNewLevel((Integer) getParam(getResource("NewLevel")));
		}
	}
}
