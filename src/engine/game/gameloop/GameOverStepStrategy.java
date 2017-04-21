package engine.game.gameloop;

import engine.game.LevelManager;

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
	protected StepStrategy getNextStepStrategy(LevelManager levelManager) {
		return new LevelStepStrategy();   //TODO change to new LevelSelectionStrategy() or similar when get there
	}

	@Override
	protected int nextLevelNumber(LevelManager levelManager) {
		return 1;
	}

	@Override
	protected boolean hasNextScreen(LevelManager levelManager) {   //TODO set to true when add LevelSelectionStrategy - actually get rid of this method then - this is TEMPORARY
		return false;
	}
}
