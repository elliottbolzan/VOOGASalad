package engine.game.gameloop;

import engine.Entity;
import engine.Event;
import engine.entities.CharacterEntity;
import engine.events.CollisionEvent;
import engine.events.KeyPressEvent;
import engine.events.TimerEvent;
import engine.game.LevelManager;
import engine.game.eventobserver.CollisionObservable;
import engine.game.eventobserver.InputObservable;
import engine.game.eventobserver.TimerObservable;
import engine.graphics.GraphicsEngine;
import engine.graphics.cameras.ScrollingCamera;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Manages the highest level of time flow in the game.
 * 
 * @author Matthew Barbano
 * @author Jay Doherty
 *
 */
public class OldGameLoopBackup {
	private static final int FRAME_TIME_MILLISECONDS = 20;
	private GraphicsEngine graphicsEngine;
	private LevelManager levelManager;
	private Timeline timeline;
	private InputObservable inputObservable;
	private CollisionObservable collisionObservable;
	private TimerObservable timerObservable;

	/**
	 * currentLevelManager is instantiated in the Game Authoring Environment
	 * (probably somewhere in the authoring.settings package)
	 * 
	 * @param currentLevelManager
	 */
	public OldGameLoopBackup(Scene gameScene, String gameFilename) {
		// Setup Observables - at beginning of entire game only
		inputObservable = new InputObservable();
		collisionObservable = new CollisionObservable();
		timerObservable = new TimerObservable();
		
		// Setup levelManager
		levelManager = new LevelManager(gameFilename);
		//levelManager.loadAllSavedLevels(gameFilename);
		
		//More Observables - at beginning of each level only
		inputObservable.setupInputListeners(gameScene);
		timerObservable.attachCurrentLevelTimerManager(levelManager.getCurrentLevel().getTimerManager());

		// Give all Events belonging to Entities in the current Level their
		// corresponding Observables
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			for (Event event : entity.getEvents()) {
				if (event instanceof KeyPressEvent) {
					event.setEventObservable(inputObservable);
				}
				else if (event instanceof CollisionEvent){
					event.setEventObservable(collisionObservable);
				}
				else if (event instanceof TimerEvent){
					event.setEventObservable(timerObservable);
				}
			}
		}

		// Attach entities in current level to observables
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			inputObservable.attach(entity);
			collisionObservable.attach(entity);
			timerObservable.attach(entity);
		}

		// Start the GAE's current level in Level Manager
		//levelManager.startCurrentLevel();

		//Set up graphical view
		setupGameView();
		
		//Start timeline
		setupTimeline();
	}

	private void setupTimeline() {
		// From ExampleBounce.java (will likely upgrade)
		KeyFrame frame = new KeyFrame(Duration.millis(FRAME_TIME_MILLISECONDS), e -> step());
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(frame);
	}

	/**
	 * External Engine API. Proceeds to the next frame of the animation while
	 * the game is playing. Responsibilities include: - loop through all
	 * Entities and call AlwaysEvent's act() (for Actions that occur on every
	 * frame, such as an enemy moving) - for all three types of Observer Events,
	 * call updateObservables() - and more
	 */
	public void startTimeline() {
		timeline.play();
	}
	
	public Pane getGameView() {
		return graphicsEngine.getView();
	}
	
	private void setupGameView() {
		graphicsEngine = new GraphicsEngine();
		graphicsEngine.setEntitiesCollection(levelManager.getCurrentLevel().getEntities());
		//TODO: Remove the following (just for tests)
		//Entity mario = new CharacterEntity("Mario", "file:" + System.getProperty("user.dir") + "/src/resources/images/mario.png");
		//mario.setX(200);
		//mario.setY(200);
		//mario.setWidth(100);
		//mario.setHeight(100);
		//levelManager.getCurrentLevel().getEntities().add(mario);		
	}

	private void step() {
		inputObservable.updateObservers();
		collisionObservable.updateObservers();
		timerObservable.updateObservers();   //ticks the clock (need to at beginning of step(), not end, because onFinished of Timeline called at END of time elapsed
		
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			entity.update();
		}

		inputObservable.setInputToProcess(false);
		
		printStepData();
		
		//OK to do for BOTH TickUp and TickDown timers!
		//This is NOT repeating the TimerObservable DP's job - this changes the game loop's flow, that calls Actions on Entities
		if(levelManager.getCurrentLevel().getTimerManager().timeIsUp()){  
			 System.out.println("Game over - you ran out of time!");
			 timeline.stop();
		}
		
		graphicsEngine.updateFrame();
	}
	
	/**
	 * Temporary, for testing. Will be removed later.
	 */
	private void printStepData(){
		System.out.println(levelManager.getCurrentLevel().getTimerManager());
	}
}
