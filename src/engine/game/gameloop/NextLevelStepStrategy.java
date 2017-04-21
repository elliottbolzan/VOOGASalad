package engine.game.gameloop;

import engine.game.LevelManager;

/**
 * 
 * @author Matthew Barbano
 *
 */
public class NextLevelStepStrategy extends TransitionStepStrategy {
	private static final String RESOURCE_NAME_REGULAR_WIN = "Win";
	private static final String RESOURCE_NAME_LAST_WIN = "WinGame";
	
	public NextLevelStepStrategy(LevelManager levelManager) {
		super(levelManager.getLevelNumber() == levelManager.getLevels().size() ? RESOURCE_NAME_LAST_WIN : RESOURCE_NAME_REGULAR_WIN);
	}
	
	@Override
	protected StepStrategy getNextStepStrategy(LevelManager levelManager) {
		return new LevelStepStrategy();   //TODO change to new LevelSelectionStrategy() or similar when get there
	}

	@Override
	protected int nextLevelNumber(LevelManager levelManager) {
		return levelManager.getLevelNumber() + 1;
	}

	@Override
	protected boolean hasNextScreen(LevelManager levelManager) {   //TODO set to true when add LevelSelectionStrategy - actually get rid of this method then - this is TEMPORARY
		return levelManager.getLevelNumber() <= levelManager.getLevels().size();
	}
}
