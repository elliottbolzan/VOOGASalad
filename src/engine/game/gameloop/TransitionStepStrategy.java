package engine.game.gameloop;

import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;

/**
 * StepStrategy for transition screen displaying messages like "Game Over" or "You won" (read 
 * from a properties file).
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
	public void setup(LevelManager levelManager, GraphicsEngine graphicsEngine,
			GameInfo info) {
		this.levelManager = levelManager;
		this.graphicsEngine = graphicsEngine;
		this.info = info;
		graphicsEngine.fillScreenWithText(resourceFileTextName);
	}

	@Override
	public void step() {
		if (frameNumber == FRAME_DURATION) {
			moveToNextScreen();
		}
		frameNumber++;
	}
	
	protected abstract int nextLevelNumber();
	
	protected abstract void handleHighscore(boolean hasNextLevel, GraphicsEngine graphicsEngine);
	
	private void moveToNextScreen() {
		levelManager.getCurrentScreen().getTimeline().stop();
		
		boolean hasNextLevel = levelManager.setLevelNumber(nextLevelNumber());
		if(hasNextLevel){
			StepStrategy nextStepStrategy = new LevelStepStrategy();
			levelManager.setCurrentStepStrategy(nextStepStrategy);
			Screen nextScreen = new Screen(levelManager, graphicsEngine, info);
			nextScreen.getTimeline().play();
		}
		else{
			handleHighscore(hasNextLevel, graphicsEngine);
		}
	}

}