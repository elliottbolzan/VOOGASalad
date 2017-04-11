package engine.actions;

import engine.Action;
import engine.game.gameloop.LevelStepStrategy;

public class NextLevelAction extends Action {
	private LevelStepStrategy levelStepStrategy;
	
	public void setLevelStepStrategy(LevelStepStrategy levelStepStrategy){
		this.levelStepStrategy = levelStepStrategy;		
	}

	@Override
	public void act() {
		getGame().getLevelStepStrategy().startNextLevel();
		levelStepStrategy.startNextLevel();
	}

}