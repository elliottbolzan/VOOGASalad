package engine.game.gameloop;

import data.Game;
import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Manages the highest level of time flow in the game. The client class for the
 * game loop.
 * 
 * @author Matthew Barbano
 *
 */

public class GameLoop {
	private ObservableBundle observableBundle;
	private Scorebar scorebar;
	private TimelineManipulator timelineManipulator;
	private LevelManager levelManager;
	private GraphicsEngine graphicsEngine;

	public GameLoop(Scene gameScene, Game game, GraphicsEngine graphicsEngine, boolean firstTimeLoading) {
		this.graphicsEngine = graphicsEngine;
		scorebar = graphicsEngine.getScorebar();
		observableBundle = new ObservableBundle(gameScene, graphicsEngine);

		levelManager = new LevelManager(game, new LevelStepStrategy(), scorebar);
		levelManager.loadAllSavedLevels(firstTimeLoading);
		if (levelManager.getLevels().size() > 0) {
			levelManager.addUnlockedLevel(1);
		}
		setupFirstStrategy();
		timelineManipulator = new TimelineManipulator(levelManager);
		GameInfo info = new GameInfo(this);
		Screen firstScreen = new Screen(levelManager, graphicsEngine, info, true);
		levelManager.setCurrentScreen(firstScreen);
		timelineManipulator.setInfo(info);
		graphicsEngine.getScorebar().setLevelManager(levelManager);
		scorebar.setupLives(levelManager, firstTimeLoading);
	}

	private void setupFirstStrategy() {
		StepStrategy firstStrategy = levelManager.getLevelSelectionScreenMode() ? new LevelSelectionStepStrategy(true)
				: new LevelStepStrategy();
		levelManager.setCurrentStepStrategy(firstStrategy);
	}

	public void startTimeline() {
		levelManager.getCurrentScreen().start();
	}

	public void pauseTimeline() {
		levelManager.getCurrentScreen().pause();
	}

	public Pane getGameView() {
		return graphicsEngine.getView();
	}

	public ObservableBundle getObservableBundle() {
		return observableBundle;
	}

	public Scorebar getScorebar() {
		return scorebar;
	}

	public TimelineManipulator timelineManipulator() {
		return timelineManipulator;
	}

	public LevelManager getLevelManager() {
		return levelManager;
	}

	public GraphicsEngine getGraphicsEngine() {
		return graphicsEngine;
	}
}
