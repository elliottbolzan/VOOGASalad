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

	protected abstract boolean handleHighscore(GraphicsEngine graphicsEngine);
	
	protected abstract void modifyUnlockedScreens();
	
	private void nextScreenLevelSelectionMode() {
		stopCurrentTimeline();
		modifyUnlockedScreens();
		if(!handleHighscore(graphicsEngine)){
			nextScreenAndStrategy(new LevelSelectionStepStrategy());
		}
	}

	private void nextScreenJustLevelsMode() {
		stopCurrentTimeline();
		if(!handleHighscore(graphicsEngine) && levelManager.setLevelNumber(nextLevelNumber())){
			nextScreenAndStrategy(new LevelStepStrategy());
		}
	}

	private void stopCurrentTimeline() {
		levelManager.getCurrentScreen().getTimeline().stop();
	}
	
	private void nextScreenAndStrategy(StepStrategy nextStepStrategy) {
		levelManager.setCurrentStepStrategy(nextStepStrategy);
		Screen nextScreen = new Screen(levelManager, graphicsEngine, info);
		nextScreen.getTimeline().play();
	}

}