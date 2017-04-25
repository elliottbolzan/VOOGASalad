package engine.game.gameloop;

import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;

/**
 * 
 * @author Matthew Barbano
 *
 */
public class NextLevelStepStrategy extends TransitionStepStrategy {
	private static final String RESOURCE_NAME_REGULAR_WIN = "Win";
	private static final String RESOURCE_NAME_LAST_WIN = "WinGame";
	private LevelManager levelManager;
	
	public NextLevelStepStrategy(LevelManager levelManager) {
		super(levelManager.getLevelNumber() == levelManager.getLevels().size() ? RESOURCE_NAME_LAST_WIN : RESOURCE_NAME_REGULAR_WIN);
		this.levelManager = levelManager;
	}
	
	@Override
	protected int nextLevelNumber() {
		return levelManager.getLevelNumber() + 1;
	}

	@Override
	protected void handleHighscore(boolean hasNextLevel, GraphicsEngine graphicsEngine) {
		if(!hasNextLevel && graphicsEngine.getScorebar().isHighscore()){
				graphicsEngine.endScreen();
		}
	}
}
