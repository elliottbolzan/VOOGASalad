package engine.game.gameloop;

import java.util.ResourceBundle;

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
	private static final String RESOURCES_NAME = "resources/Strings";
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
		graphicsEngine.fillScreenWithText(ResourceBundle.getBundle(RESOURCES_NAME).getString(resourceFileTextName));
	}

	@Override
	public void step() {
		if (frameNumber == FRAME_DURATION) {
			moveToNextScreen();
		}
		frameNumber++;
	}
	
	protected GraphicsEngine getEngine(){
		return graphicsEngine;
	}

	protected abstract StepStrategy getNextStepStrategy(LevelManager levelManager);

	protected abstract int nextLevelNumber(LevelManager levelManager);

	protected abstract boolean hasNextScreen(LevelManager levelManager);

	private void moveToNextScreen() {
		levelManager.getCurrentScreen().getTimeline().stop();
		boolean hasNextLevel = levelManager.setLevelNumber(nextLevelNumber(levelManager));
		if (hasNextLevel && hasNextScreen(levelManager)) {
			StepStrategy nextStepStrategy = getNextStepStrategy(levelManager);
			levelManager.setCurrentStepStrategy(nextStepStrategy);
			Screen nextScreen = new Screen(levelManager, graphicsEngine, info);
			nextScreen.getTimeline().play();
		}
		// TODO Throw exception here or do something...
	}

}