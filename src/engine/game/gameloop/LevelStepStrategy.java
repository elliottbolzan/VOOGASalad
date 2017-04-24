package engine.game.gameloop;

import engine.Action;
import engine.Entity;
import engine.entities.CameraEntity;
import engine.Event;
import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;

/**
 * Subclass of StepStrategy implementing step() when a Level should be
 * displayed.
 * 
 * @author Matthew Barbano
 *
 */
public class LevelStepStrategy implements StepStrategy {
	private LevelManager levelManager;
	private GraphicsEngine graphicsEngine;
	private GameInfo info;
	private boolean screenFinished;
	private StepStrategy nextStepStrategy;

	/**
	 * Functionality executed when timeline for Screen with this
	 * LevelStepStrategy is step up; only executed once. Called from Screen's
	 * constructor.
	 */
	@Override
	public void setup(LevelManager levelManager, GraphicsEngine graphicsEngine, GameInfo info) {
		this.levelManager = levelManager;
		this.graphicsEngine = graphicsEngine;
		this.info = info;
		this.screenFinished = false;
		levelManager.resetCurrentLevel();
		info.getScorebar().resetTimerManager();
		setupGameView();
		addInfoToEntities();
	}

	public void flagScreenFinished(StepStrategy nextStepStrategy) {
		this.screenFinished = true;
		this.nextStepStrategy = nextStepStrategy;
	}

	public boolean screenFinished() {
		return screenFinished;
	}

	/**
	 * Called on every iteration of the Timeline.
	 * 
	 * Note to programmer: Among other things, ticks the clock, so need at
	 * beginning of step(), not end, because onFinished of Timeline called at
	 * END of time elapsed.
	 * 
	 */
	@Override
	public void step() {
		info.getObservableBundle().updateObservers();
		// TODO If need an update method in GameInfo, update it here, right
		// before entity.update();
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			entity.update();
		}
		info.getObservableBundle().getCollisionObservable().getCollisions().clear();
		info.getObservableBundle().getInputObservable().setInputToProcess(false);
		graphicsEngine.updateFrame();
		if (screenFinished) {
			levelManager.setCurrentStepStrategy(nextStepStrategy);
			Screen nextScreen = new Screen(levelManager, graphicsEngine, info);
			nextScreen.getTimeline().play();
		}

	}

	/**
	 * Helper grouping all the observable logic in this class for setup.
	 */
	private void addInfoToEntities() {
		info.getObservableBundle().levelObservableSetup(info);
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			entity.setGameInfo(info);
			info.getObservableBundle().attachEntityToAll(entity);
			for (Event event : entity.getEvents()) {
				event.setGameInfo(info);
				for (Action action : event.getActions()) {
					action.setGameInfo(info);
				}
			}
		}
	}

	private void setupGameView() {
		CameraEntity levelCamera = levelManager.getCurrentLevel().getCamera();
		levelManager.getCurrentLevel().getEntities().add(levelCamera);
		graphicsEngine.setCamera(levelCamera);
		graphicsEngine.setEntitiesCollection(levelManager.getCurrentLevel().getEntities());
	}
}