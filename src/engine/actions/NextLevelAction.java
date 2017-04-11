package engine.actions;

import engine.Action;
import engine.game.gameloop.LevelStepStrategy;

public class NextLevelAction extends Action {

	@Override
	public void act() {
		((LevelStepStrategy) getGameInfo().getCurrentStepStrategy()).startNextLevel();
	}

}