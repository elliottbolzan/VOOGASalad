package engine.game.gameloop;

import engine.game.LevelManager;

public class GameOverStepStrategy extends TransitionStepStrategy {
	private static final String RESOURCE_NAME = "GameOver";
	
	public GameOverStepStrategy() {
		super(RESOURCE_NAME);
	}
	
	/**
	 * Subclass specific text based on variables, not on resource file.
	 */
	/*
	@Override
	protected String getSubclassSpecificText() {
		return "";
	}
	*/
	
	@Override
	protected StepStrategy getNextStepStrategy(LevelManager levelManager) {
		return new LevelStepStrategy();   //TODO change to new LevelSelectionStrategy() or similar when get there
	}

	@Override
	protected int nextLevelNumber(LevelManager levelManager) {
		return 1;
	}
}
