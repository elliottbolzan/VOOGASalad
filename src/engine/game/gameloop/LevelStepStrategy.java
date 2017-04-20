package engine.game.gameloop;
import engine.Action;
import engine.Entity;
import engine.Event;
import engine.GameInfo;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import javafx.scene.Scene;

/**
 * Subclass of StepStrategy implementing step() when a Level should be displayed.
 * 
 * @author Matthew Barbano
 *
 */
public class LevelStepStrategy implements StepStrategy {
	private LevelManager levelManager;
	private Scene gameScene;
	private GraphicsEngine graphicsEngine;
	private Screen screen;
	private GameInfo info;
	
	/**
	 * Functionality executed when timeline for Screen with this LevelStepStrategy is step up; only executed once.
	 * Called from Screen's constructor.	
	 */
	@Override
	public void setup(LevelManager levelManager, Scene gameScene, Screen screen, GraphicsEngine graphicsEngine,
			GameInfo info) {
		this.levelManager = levelManager;
		this.gameScene = gameScene;
		this.graphicsEngine = graphicsEngine;
		this.screen = screen;
		this.info = info;
		
		levelManager.loadAllSavedLevels();
		info.getScorebar().resetTimerManager();
		addInfoToEntities();
		setupGameView();
	}
	
	/**
	 * Called on every iteration of the Timeline.
	 * 
	 * Note to programmer: Among other things, ticks the clock, so need at beginning of step(), not end, because onFinished of Timeline called at END of time elapsed.
	 * 
	 */
	@Override
	public void step() {
		info.getObservableBundle().updateObservers();
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			entity.update();
		}
		info.getObservableBundle().getCollisionObservable().getCollisions().clear();
		info.getObservableBundle().getInputObservable().setInputToProcess(false);
		graphicsEngine.updateFrame();
	}
	
	/**
	 * Logic for ending this level screen when you die. IMPORTANT: Called from DieAction
	 * (and can be called from other Actions), NOT from step(). Stops this screen's
	 * timeline, instantiates the next screen with a TransitionStepStrategy
	 * appropriate to whether there is a gameOver, and starts that timeline.
	 * Although this method uses a Timeline, it is specific to Level Screens, so
	 * I put it here in LevelStepStrategy rather than in Screen.
	 * 
	 * @param gameOver
	 */
	
	public void endLevel(boolean gameOver) {
		for(Entity entity : levelManager.getCurrentLevel().getEntities()){
			info.getObservableBundle().detachEntityFromAll(entity);
		}

		StepStrategy nextStepStrategy;
		if (gameOver) {
			nextStepStrategy = new GameOverStepStrategy();
			graphicsEngine.getScorebar().saveFinalScore();
		} else {
			nextStepStrategy = new LoseLifeStepStrategy();
		}
		info.setCurrentStepStrategy(nextStepStrategy);
		screen.getTimeline().stop();
		Screen nextScreen = new Screen(nextStepStrategy, levelManager, gameScene, graphicsEngine, info);
		nextScreen.getTimeline().play();
	}
	
	/**
	 * Helper grouping all the observable logic in this class for setup.
	 */
	private void addInfoToEntities() {
		info.getObservableBundle().levelObservableSetup(gameScene, levelManager, info);
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
		// TODO call graphicsEngine.setCamera() here
		graphicsEngine.setEntitiesCollection(levelManager.getCurrentLevel().getEntities());
	}
	
	/**
	 * Logic for ending this level screen when won the level. IMPORTANT: Called from NextLevelAction
	 * (and can be called from other Actions), NOT from step(). Stops this screen's
	 * timeline, instantiates the next screen with a NextLevelStepStrategy, and starts that timeline.
	 * Although this method uses a Timeline, it is specific to Level Screens, so
	 * I put it here in LevelStepStrategy rather than in Screen.
	 */
	public void startNextLevel() {
		StepStrategy nextStepStrategy = new NextLevelStepStrategy();
		graphicsEngine.getScorebar().saveFinalScore();
		
		info.setCurrentStepStrategy(nextStepStrategy);
		screen.getTimeline().stop();
		Screen nextScreen = new Screen(nextStepStrategy, levelManager, gameScene, graphicsEngine, info);
		nextScreen.getTimeline().play();
	}
}