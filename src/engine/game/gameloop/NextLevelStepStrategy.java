package engine.game.gameloop;

import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;

/**
 * 
 * @author Matthew Barbano
 *
 */
public class NextLevelStepStrategy extends TransitionStepStrategy {
	private static final String RESOURCE_NAME_REGULAR_WIN = "Win";
	private static final String RESOURCE_NAME_LAST_WIN = "WinGame";
	private LevelManager levelManager;
	
	public NextLevelStepStrategy(LevelManager levelManager) {
		super(levelManager.getLevelNumber() == levelManager.getLevels().size() ? RESOURCE_NAME_LAST_WIN : RESOURCE_NAME_REGULAR_WIN);
		this.levelManager = levelManager;
	}
	
	@Override
	protected int nextLevelNumber() {
		return levelManager.getLevelNumber() + 1;
	}

	@Override
	protected boolean handleHighscore(GraphicsEngine graphicsEngine) {
		System.out.println(levelManager.getLevelNumber() == levelManager.getLevels().size());
		System.out.println(graphicsEngine.getScorebar().isHighscore());
		boolean handled = levelManager.getLevelNumber() == levelManager.getLevels().size() && graphicsEngine.getScorebar().isHighscore();
		if(handled){
			System.out.println("Calling high scores screen");
			graphicsEngine.endScreen();
		}
		return handled;
		/*
		if(!hasNextLevel && graphicsEngine.getScorebar().isHighscore()){
				graphicsEngine.endScreen();
		}
		*/
	}

	@Override
	protected void modifyUnlockedScreens() {
		levelManager.addUnlockedLevel(levelManager.getLevelNumber() + 1);
	}
}
