package engine.game.gameloop;

import engine.Action;
import engine.Entity;
import engine.Event;
import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import javafx.scene.Scene;

/**
 * Subclass of StepStrategy implementing step() when a Level should be
 * displayed.
 * 
 * @author Matthew Barbano
 *
 */
public class LevelStepStrategy implements StepStrategy {
	// private ObservableBundle observableBundle;
	private LevelManager levelManager;
	private Scene gameScene;
	private GraphicsEngine graphicsEngine;
	private Screen screen;
	private GameInfo info;

	/**
	 * Functionality executed when timeline for Screen with this
	 * LevelStepStrategy is step up; only executed once. Called from Screen's
	 * constructor.
	 */
	@Override
	public void setup(Scene gameScene, Screen screen, GraphicsEngine graphicsEngine, GameInfo info) {
		// this.observableBundle = newObservableBundle;
		this.levelManager = info.getLevelManager();
		this.gameScene = gameScene;
		this.graphicsEngine = graphicsEngine;
		this.screen = screen;
		this.info = info;
		levelManager.loadAllSavedLevels(); // To reset initial state of level
		levelManager.getCurrentLevel().addEntity(graphicsEngine.getCamera()); // TODO:
																				// add
																				// the
																				// camera
																				// in
																				// a
																				// cleaner
																				// way
		// TODO get filename here

		info.getScorebar().resetTimerManager();

		// instantiateTestEntitesEventsActions();
		// connectObservablesToLevels();
		// setLevelStepStrategyInDieActions();
		addInfoToEntities();
		// levelManager.startCurrentLevel(); //TODO sets Entities to initial
		// conditions - need to ask Nikita how to do this
		setupGameView();
	}

	/**
	 * Called on every iteration of the Timeline.
	 */
	@Override
	public void step() {
		info.getObservableBundle().updateObservers(); // ticks the clock (need
														// to at
		// beginning of step(), not end,
		// because onFinished of Timeline
		// called at END of time elapsed
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			entity.update();
		}
		info.getObservableBundle().getCollisionObservable().getCollisions().clear();
		info.getObservableBundle().getInputObservable().setInputToProcess(false);
		graphicsEngine.updateFrame();
	}

	/**
	 * Logic for ending this level screen when you die. IMPORTANT: Called from
	 * DieAction (and can be called from other Actions), NOT from step(). Stops
	 * this screen's timeline, instantiates the next screen with a
	 * TransitionStepStrategy appropriate to whether there is a gameOver, and
	 * starts that timeline. Although this method uses a Timeline, it is
	 * specific to Level Screens, so I put it here in LevelStepStrategy rather
	 * than in Screen.
	 * 
	 * @param gameOver
	 */

	// TODO Rename this die()
	public void endLevel(boolean gameOver) {
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			info.getObservableBundle().detachEntityFromAll(entity);
		}
		// Do not check timeIsUp() here, rather, set up TimerEvent with time = 0
		// and attach a DieAction, which will call this method when appropriate
		StepStrategy nextStepStrategy;
		if (gameOver) {
			// screen.setNextScreen(screen); //TODO get rid of next screen
			// parameter - no need to keep track of!

			nextStepStrategy = new GameOverStepStrategy();
			graphicsEngine.getScorebar().saveFinalScore();
		} else {
			// screen.setNextScreen(screen);
			nextStepStrategy = new LoseLifeStepStrategy();
		}
		info.setCurrentStepStrategy(nextStepStrategy);
		screen.getTimeline().stop();
		Screen nextScreen = new Screen(nextStepStrategy, gameScene, graphicsEngine, info);
		nextScreen.getTimeline().play();
	}

	/**
	 * Helper grouping all the observable logic in this class for setup.
	 */
	private void addInfoToEntities() {
		info.getObservableBundle().levelObservableSetup(gameScene, levelManager, info);
		info.setLevelManager(this.levelManager);
		// info.getObservableBundle().setObservablesInEvents(levelManager);
		// //<------------------NEED TO REMOVE<--------------
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			entity.setGameInfo(info);
			info.getObservableBundle().attachEntityToAll(entity); // Good; this
																	// replaces
																	// attachEntityToAll()
			for (Event event : entity.getEvents()) {
				event.setGameInfo(info);
				for (Action action : event.getActions()) {
					action.setGameInfo(info);
				}
			}
		}
	}

	private void setupGameView() {
		// TODO call graphicsEngine.setCamera() here
		graphicsEngine.setEntitiesCollection(levelManager.getCurrentLevel().getEntities());
	}

	/**
	 * Logic for ending this level screen when won the level. IMPORTANT: Called
	 * from NextLevelAction (and can be called from other Actions), NOT from
	 * step(). Stops this screen's timeline, instantiates the next screen with a
	 * NextLevelStepStrategy, and starts that timeline. Although this method
	 * uses a Timeline, it is specific to Level Screens, so I put it here in
	 * LevelStepStrategy rather than in Screen.
	 */
	public void startNextLevel() {
		StepStrategy nextStepStrategy = new NextLevelStepStrategy();
		graphicsEngine.getScorebar().saveFinalScore();

		info.setCurrentStepStrategy(nextStepStrategy);
		screen.getTimeline().stop();
		Screen nextScreen = new Screen(nextStepStrategy, gameScene, graphicsEngine, info);
		nextScreen.getTimeline().play();
	}
}