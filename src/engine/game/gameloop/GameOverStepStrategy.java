package engine.game.gameloop;

import engine.graphics.GraphicsEngine;

/**
 * 
 * @author Matthew Barbano
 *
 */
public class GameOverStepStrategy extends TransitionStepStrategy {
	private static final String RESOURCE_NAME = "GameOver";
	
	public GameOverStepStrategy() {
		super(RESOURCE_NAME);
	}

	@Override
	protected int nextLevelNumber() {
		return -1;
	}

	@Override
	protected void handleHighscore(boolean hasNextLevel, GraphicsEngine graphicsEngine) {
		//Intentionally left blank.
	}
}
