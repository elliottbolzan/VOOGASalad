package engine.game.gameloop;

import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;

/**
 * StepStrategy for transition screen displaying messages like "Game Over" or
 * "You won" (read from a properties file).
 * 
 * @author Matthew Barbano
 *
 */
public abstract class TransitionStepStrategy implements StepStrategy {
	private static final int FRAME_DURATION = 150;

	private int frameNumber = 1;
	private String resourceFileTextName;

	private LevelManager levelManager;
	private GraphicsEngine graphicsEngine;
	private GameInfo info;

	public TransitionStepStrategy(String resourceFileTextName) {
		this.resourceFileTextName = resourceFileTextName;
	}

	@Override
	public void setup(LevelManager levelManager, GraphicsEngine graphicsEngine, GameInfo info) {
		this.levelManager = levelManager;
		this.graphicsEngine = graphicsEngine;
		this.info = info;
		graphicsEngine.fillScreenWithText(resourceFileTextName);
	}

	@Override
	public void step() {
		if (frameNumber == FRAME_DURATION && levelManager.getLevelSelectionScreenMode()) {
			nextScreenLevelSelectionMode();
		} 
		else if(frameNumber == FRAME_DURATION){
			nextScreenJustLevelsMode();
		}
		frameNumber++;
	}

	protected abstract int nextLevelNumber();

	protected abstract void handleHighscore(boolean hasNextLevel, GraphicsEngine graphicsEngine);
	
	protected abstract void modifyUnlockedScreens();
	
	protected abstract void handleHighscoreLevelSelectionMode();
	
	//Possibly more for template DP here
	
	private void nextScreenLevelSelectionMode() {
		levelManager.getCurrentScreen().getTimeline().stop();
		
		modifyUnlockedScreens();
		
		
		//ACTUALLY THIS IS THE TEMPLATE DP!
		//TODO Convert each of these 3 cases into a Strategy DP?, or this and next method to strategy DP?
		//If tree here
		//Modify unlocked screens (subclass method call) - YES, do it here
		//for "won" case, take care of high score
		//reset nextStepStrategy, new Screen, and play the new timeline
	}

	private void nextScreenJustLevelsMode() {
		levelManager.getCurrentScreen().getTimeline().stop();

		boolean hasNextLevel = levelManager.setLevelNumber(nextLevelNumber());
		if (hasNextLevel) {
			StepStrategy nextStepStrategy = new LevelStepStrategy();
			levelManager.setCurrentStepStrategy(nextStepStrategy);
			Screen nextScreen = new Screen(levelManager, graphicsEngine, info);
			nextScreen.getTimeline().play();
		} else {
			handleHighscore(hasNextLevel, graphicsEngine);
		}
	}

}