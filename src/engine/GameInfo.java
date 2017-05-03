package engine;

import engine.game.LevelManager;
import engine.game.gameloop.GameLoop;
import engine.game.gameloop.ObservableBundle;
import engine.game.gameloop.Scorebar;
import engine.game.gameloop.TimelineManipulator;
import engine.graphics.GraphicsEngine;

/**
 * 
 * This class is used to convey information about the current status of the game
 * from things that monitor the status of the game (collision detection, timer,
 * input detection, etc), to things that observe that status (actions, events,
 * etc).
 * 
 * @author nikita matt
 */
public class GameInfo {
	private ObservableBundle bundle;
	private Scorebar scorebar;
	private TimelineManipulator timelineManipulator;
	private LevelManager levelManager;
	private GraphicsEngine graphicsEngine;
	private boolean entitiesNeverUpdated;

	public GameInfo(GameLoop gameLoop) {
		this.bundle = gameLoop.getObservableBundle();
		this.scorebar = gameLoop.getScorebar();
		this.timelineManipulator = gameLoop.timelineManipulator();
		this.levelManager = gameLoop.getLevelManager();
		this.graphicsEngine = gameLoop.getGraphicsEngine();
		this.entitiesNeverUpdated = true;
	}

	public void setEntitiesNeverUpdatedFalse() {
		entitiesNeverUpdated = false;
	}

	public boolean getEntitiesNeverUpdated() {
		return entitiesNeverUpdated;
	}

	public LevelManager getLevelManager() {
		return levelManager;
	}

	public ObservableBundle getObservableBundle() {
		return bundle;
	}

	public TimelineManipulator getTimelineManipulator() {
		return timelineManipulator;
	}

	public Scorebar getScorebar() {
		return scorebar;
	}

	public GraphicsEngine getGraphicsEngine() {
		return graphicsEngine;
	}
}