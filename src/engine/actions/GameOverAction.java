package engine.actions;

import engine.Action;
import engine.game.gameloop.LevelStepStrategy;

public class GameOverAction extends Action {

	@Override
	public void act() {
		if (!((LevelStepStrategy) getGameInfo().getLevelManager().getCurrentStepStrategy()).screenFinished()) {
			getGameInfo().getTimelineManipulator().die(true);
		}
	}

}
