package engine.game.gameloop;

import java.util.List;
import java.util.stream.Collectors;

import engine.GameInfo;
import engine.actions.Action;
import engine.entities.Entity;
import engine.entities.entities.CameraEntity;
import engine.events.Event;
import engine.events.regular_events.InsideCameraRegionEvent;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import exceptions.ActionException;

/**
 * Subclass of StepStrategy implementing step() when a Level should be
 * displayed.
 * 
 * @author Matthew Barbano
 *
 */
public class LevelStepStrategy implements StepStrategy
{
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
	public void setup(LevelManager levelManager, GraphicsEngine graphicsEngine, GameInfo info)
	{
		this.levelManager = levelManager;
		this.graphicsEngine = graphicsEngine;
		this.info = info;
		this.screenFinished = false;
		levelManager.resetCurrentLevel();
		info.getScorebar().resetTimerManager();
		setupGameView();
		addInfoToEntities();
	}

	public void flagScreenFinished(StepStrategy nextStepStrategy)
	{
		this.screenFinished = true;
		this.nextStepStrategy = nextStepStrategy;
	}

	public boolean screenFinished()
	{
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
	public void step()
	{
		InsideCameraRegionEvent event = new InsideCameraRegionEvent();
		List<Entity> observersTemp = info.getObservableBundle().getCollisionObservable().getObservers();
		info.getObservableBundle().getCollisionObservable()
				.setObservers(info.getObservableBundle().getCollisionObservable().getObservers().stream().filter(s -> {
					event.setEntity(s);
					return event.act();
				}).collect(Collectors.toList()));
		info.getObservableBundle().updateObservers();
		levelManager.getCurrentLevel().getEntities().stream().filter(s -> {
			event.setEntity(s);
			return event.act();
		}).forEach(e -> e.update());
		info.setEntitiesNeverUpdatedFalse();
		info.getObservableBundle().getCollisionObservable().getCollisions().clear();
		info.getObservableBundle().getInputObservable().setInputToProcess(false);
		graphicsEngine.updateFrame();
		if (screenFinished) {
			levelManager.setCurrentStepStrategy(nextStepStrategy);
			Screen nextScreen = new Screen(levelManager, graphicsEngine, info, false);
			nextScreen.getTimeline().play();
		}
		info.getObservableBundle().getCollisionObservable().setObservers(observersTemp);
	}

	/**
	 * Helper grouping all the observable logic in this class for setup.
	 */
	private void addInfoToEntities()
	{
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

	private void setupGameView()
	{
		CameraEntity levelCamera = levelManager.getCurrentLevel().getCamera();
		levelManager.getCurrentLevel().getEntities().add(levelCamera);
		graphicsEngine.setupLevel(levelManager.getCurrentLevel());
	}
}