package engine.game.gameloop;

import engine.graphics.GraphicsEngine;

/**
 * 
 * @author Matthew Barbano
 *
 */
public class LoseLifeStepStrategy extends TransitionStepStrategy {
	private static final String RESOURCE_NAME = "LivesLeft";
	
	public LoseLifeStepStrategy() {
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
