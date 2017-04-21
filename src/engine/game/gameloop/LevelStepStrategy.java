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
		addInfoToEntities();
		setupGameView();
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			if (entity.getName().equals("Luigi")) {
				System.out.println("x = " + entity.getX() + ", y = " + entity.getY());
			}
		}
		printAllGameObjects();
	}
	
	//For testing, to be removed after
	public void printAllGameObjects(){
		System.out.println("Collisions during setup" + info.getObservableBundle().getCollisionObservable().getCollisions());
		
		/*
		for(Entity entity : levelManager.getCurrentLevel().getEntities()){
			System.out.print(entity + " " + entity.getName());
			System.out.print("[");
			for(Event event : entity.getEvents()){
				System.out.print(event + " ");
				System.out.print("[");
				for(Action action : event.getActions()){
					System.out.print(action + " ");
				}
				System.out.print("]");
			}
			System.out.print("]");
		}
		System.out.println();
		*/
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
		System.out.println("Collisions during step" + info.getObservableBundle().getCollisionObservable().getCollisions());
		info.getObservableBundle().updateObservers();
		System.out.println("Collisions during step" + info.getObservableBundle().getCollisionObservable().getCollisions());
		// TODO If need an update method in GameInfo, update it here, right
		// before entity.update();
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			entity.update();
		}
		info.getObservableBundle().getCollisionObservable().getCollisions().clear();
		System.out.println("Collisions after clearing: " + info.getObservableBundle().getCollisionObservable().getCollisions());
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
		// TODO call graphicsEngine.setCamera() here
		graphicsEngine.setEntitiesCollection(levelManager.getCurrentLevel().getEntities());
	}
}