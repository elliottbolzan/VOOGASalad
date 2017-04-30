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
	private LevelManager levelManager;
	private int newLevel;

	public NewLevelStepStrategy(LevelManager levelManager, int newLevel) {
		super(levelManager.getLevelNumber() == levelManager.getLevels().size() ? RESOURCE_NAME_LAST_WIN : RESOURCE_NAME_REGULAR_WIN);
		this.levelManager = levelManager;
		this.newLevel = newLevel;
	}

	@Override
	protected int nextLevelNumber() {
		return newLevel;
	}

	@Override
	protected boolean handleHighscore(GraphicsEngine graphicsEngine) {
		boolean handled = levelManager.getLevelNumber() == levelManager.getLevels().size() && graphicsEngine.getScorebar().isHighscore();
		if(handled){
			graphicsEngine.endGame();
		}
		return handled;
	}

	@Override
	protected void modifyUnlockedScreens() {
		levelManager.addUnlockedLevel(newLevel);
	}

	@Override
	protected StepStrategy nextStrategyLevelSelectionMode() {
		return new LevelSelectionStepStrategy(false);
	}
}