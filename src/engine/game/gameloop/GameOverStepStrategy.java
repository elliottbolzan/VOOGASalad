package engine.game.gameloop;

import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;

/**
 * 
 * @author Matthew Barbano
 *
 */
public class GameOverStepStrategy extends TransitionStepStrategy {
	private static final String RESOURCE_NAME = "GameOver";
	private LevelManager levelManager;
	
	public GameOverStepStrategy(LevelManager levelManager) {
		super(RESOURCE_NAME);
		this.levelManager = levelManager;
	}

	@Override
	protected int nextLevelNumber() {
		return -1;
	}

	@Override
	protected void handleHighscore(boolean hasNextLevel, GraphicsEngine graphicsEngine) {
		//Intentionally left blank.
	}

	@Override
	protected void modifyUnlockedScreens() {
		levelManager.clearUnlockedLevels();
	}
}
