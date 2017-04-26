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
	protected boolean handleHighscore(GraphicsEngine graphicsEngine) {
		return false;
	}

	@Override
	protected void modifyUnlockedScreens() {
		//Intentionally left blank.
	}
	
	@Override
	protected StepStrategy nextStrategyLevelSelectionMode() {
		return new LevelStepStrategy();
	}
}
