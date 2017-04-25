package engine.game.gameloop;

import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;

/**
 * 
 * @author Matthew Barbano
 *
 */
public class LoseLifeStepStrategy extends TransitionStepStrategy {
	private static final String RESOURCE_NAME = "LivesLeft";
	private LevelManager levelManager;
	
	public LoseLifeStepStrategy(LevelManager levelManager) {
		super(RESOURCE_NAME);
		this.levelManager = levelManager;
	}

	@Override
	protected int nextLevelNumber() {
		return levelManager.getLevelNumber();
	}

	@Override
	protected void handleHighscore(boolean hasNextLevel, GraphicsEngine graphicsEngine) {
		//Intentionally left blank.
	}

	@Override
	protected void modifyUnlockedScreens() {
		//Intentionally left blank.
	}

	@Override
	protected void handleHighscoreLevelSelectionMode() {
		// TODO Auto-generated method stub
		
	}
}
