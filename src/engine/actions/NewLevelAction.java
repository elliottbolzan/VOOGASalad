package engine.actions;

import engine.Action;
import engine.Parameter;
import engine.game.gameloop.LevelStepStrategy;

/**
 * Terminates the current level and starts any level of the game, given
 * by the parameter.
 * @author Matthew Barbano
 *
 */
public class NewLevelAction extends Action{
	
	//TODO error checking that the level exists
	
	public NewLevelAction() {
		addParam(new Parameter("New Level", Integer.class, 0));
	}
	
	@Override
	public void act() {
		try {
			// This check added to fix bug of multiple NextLevelActions triggering
			if (!((LevelStepStrategy) getGameInfo().getLevelManager().getCurrentStepStrategy()).screenFinished()) {
				getGameInfo().getTimelineManipulator().startNewLevel((Integer) getParam("New Level"));
			}
		} catch (ClassCastException e) {
			System.out.println("Casting error in NewLevelAction");
		}
	}
	
}
