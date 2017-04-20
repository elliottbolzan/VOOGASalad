package engine.game.gameloop;

import java.util.ResourceBundle;

import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import javafx.scene.Scene;

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
	private Scene gameScene;
	private GraphicsEngine graphicsEngine;
	private Screen screen;
	private GameInfo info;

	public TransitionStepStrategy(String resourceFileTextName) {
		this.resourceFileTextName = resourceFileTextName;
	}

	@Override
	public void setup(LevelManager levelManager, Scene gameScene, Screen screen, GraphicsEngine graphicsEngine,
			GameInfo info) {
		this.levelManager = levelManager;
		this.gameScene = gameScene;
		this.graphicsEngine = graphicsEngine;
		this.screen = screen;
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

	protected abstract StepStrategy getNextStepStrategy(LevelManager levelManager);

	protected abstract int nextLevelNumber(LevelManager levelManager);

	protected abstract boolean hasNextScreen(LevelManager levelManager);

	private void moveToNextScreen() {
		screen.getTimeline().stop();
		boolean hasNextLevel = levelManager.setLevelNumber(nextLevelNumber(levelManager));
		if (hasNextLevel && hasNextScreen(levelManager)) {
			StepStrategy nextStepStrategy = getNextStepStrategy(levelManager);
			info.setCurrentStepStrategy(nextStepStrategy);
			Screen nextScreen = new Screen(nextStepStrategy, levelManager, gameScene, graphicsEngine, info);
			nextScreen.getTimeline().play();
		}
		// TODO Throw exception here or do something...
	}

}
