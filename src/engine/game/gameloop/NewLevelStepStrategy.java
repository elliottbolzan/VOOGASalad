package engine.game.gameloop;

import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;

/**
 * 
 * @author Matthew Barbano
 *
 */
public class NewLevelStepStrategy extends TransitionStepStrategy {
	private static final String RESOURCE_NAME_REGULAR_WIN = "Win";
	private static final String RESOURCE_NAME_LAST_WIN = "WinGame";
	private int newLevel;
	
	public NewLevelStepStrategy(LevelManager levelManager, int newLevel) {
		super(levelManager.getLevelNumber() == levelManager.getLevels().size() ? RESOURCE_NAME_LAST_WIN : RESOURCE_NAME_REGULAR_WIN);
		this.newLevel = newLevel;
	}

	@Override
	protected int nextLevelNumber() {
		return newLevel;
	}

	@Override
	protected void handleHighscore(boolean hasNextLevel, GraphicsEngine graphicsEngine) {
		if(!hasNextLevel && graphicsEngine.getScorebar().isHighscore()){
			graphicsEngine.endScreen();
		}
	}
}