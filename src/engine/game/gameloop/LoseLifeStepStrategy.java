package engine.game.gameloop;

import engine.game.LevelManager;

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
	protected StepStrategy getNextStepStrategy(LevelManager levelManager) {
		return new LevelStepStrategy(); 
	}

	@Override
	protected int nextLevelNumber(LevelManager levelManager) {
		return levelManager.getLevelNumber();
	}

	@Override
	protected boolean hasNextScreen(LevelManager levelManager) {
		return true;
	}
}
