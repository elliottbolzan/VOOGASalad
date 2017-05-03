package engine.game.gameloop;

import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;

/**
 * TransitionStepStrategy for when the hero loses a life (but does not have a
 * game over).
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
	}

	@Override
	protected StepStrategy nextStrategyLevelSelectionMode() {
		return new LevelStepStrategy();
	}
}
