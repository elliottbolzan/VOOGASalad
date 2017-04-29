package engine.game.gameloop;

import engine.GameInfo;
import engine.entities.Entity;
import engine.entities.entities.CharacterEntity;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import game_data.Game;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Manages the highest level of time flow in the game. The client class for the
 * game loop.
 * 
 * @author Matthew Barbano
 *
 */

//This is a git test.

public class GameLoop {
	private ObservableBundle observableBundle;
	private Scorebar scorebar;
	private TimelineManipulator timelineManipulator;
	private LevelManager levelManager;
	private GraphicsEngine graphicsEngine;

	public GameLoop(Scene gameScene, Game game, GraphicsEngine graphicsEngine) {
		this.graphicsEngine = graphicsEngine;
		scorebar = graphicsEngine.getScorebar();
		observableBundle = new ObservableBundle(gameScene);

		levelManager = new LevelManager(game, new LevelStepStrategy(), scorebar);
		levelManager.loadAllSavedLevels();
		if (levelManager.getLevels().size() > 0) {
			levelManager.addUnlockedLevel(1);
		} else {
			// TODO convert to exception
			System.out.println("Error in GameLoop.java - game has no levels.");
		}
		setupFirstStrategy();

		timelineManipulator = new TimelineManipulator(levelManager);
		GameInfo info = new GameInfo(this);
		Screen firstScreen = new Screen(levelManager, graphicsEngine, info, true);
		levelManager.setCurrentScreen(firstScreen);
		timelineManipulator.setInfo(info);
		graphicsEngine.getScorebar().setLevelManager(levelManager);
		
		int initialLives = -1;
		for(Entity entity : levelManager.getCurrentLevel().getEntities()){
			if(entity instanceof CharacterEntity){
				((CharacterEntity) entity).initializeInitialLives();
				initialLives = ((CharacterEntity) entity).getInitialLives();
			}
		}
		levelManager.setCarryoverLives(initialLives);
	}

	private void setupFirstStrategy() {
		// TODO set level selection screen mode from GAE here
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
