package engine.game.gameloop;

import engine.Action;
import engine.Collision;
import engine.CollisionSide;
import engine.Entity;
import engine.Event;
import engine.actions.DieAction;
import engine.actions.JumpSpeedAction;
import engine.actions.MoveAction;
import engine.actions.WalkAction;
import engine.actions.ZeroHorizontalSpeedAction;
import engine.actions.ZeroVerticalSpeedAction;
import engine.entities.BlockEntity;
import engine.entities.CharacterEntity;
import engine.events.AlwaysEvent;
import engine.events.CollisionEvent;
import engine.events.KeyPressEvent;
import engine.events.KeyReleaseEvent;
import engine.events.TimerEvent;
import engine.game.Level;
import engine.game.LevelManager;
import engine.graphics.GraphicsEngine;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class LevelStepStrategy implements StepStrategy{
	private ObservableBundle observableBundle;
	private LevelManager levelManager;
	private Scene gameScene;
	private GraphicsEngine graphicsEngine;
	private Screen screen;

	@Override
	public void setup(ObservableBundle newObservableBundle, LevelManager levelManager, Scene gameScene, Screen screen, GraphicsEngine graphicsEngine) {
		this.observableBundle = newObservableBundle;
		this.levelManager = levelManager;
		this.gameScene = gameScene;
		this.graphicsEngine = graphicsEngine;
		this.screen = screen;
		
		levelManager.loadAllSavedLevels(); //To reset initial state of level TODO get filename here
		
		instantiateTestEntitesEventsActions();
		
		connectObservablesToLevels();
		setLevelStepStrategyInDieActions();
		//levelManager.startCurrentLevel(); //TODO sets Entities to initial conditions - need to ask Nikita how to do this
		setupGameView();
	}
	
	@Override
	public void step() {
		System.out.println("Executing Level's step()");
		observableBundle.updateObservers();  //ticks the clock (need to at beginning of step(), not end, because onFinished of Timeline called at END of time elapsed
		
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			entity.update();
		}
		
		observableBundle.getCollisionObservable().getCollisions().clear();
		observableBundle.getInputObservable().setInputToProcess(false);
		graphicsEngine.updateFrame();
		printStepData(); //TODO Remove after debugging
	}
	
	/**
	 * Although this method uses a Timeline, it is specific to Level Screens, so I put it here in LevelStepStrategy
	 * rather than in Screen.
	 * @param gameOver
	 */
	public void endLevel(boolean gameOver){
		//Do not check timeIsUp() here, rather, set up TimerEvent with time = 0 and attach a DieAction, which will call this method when appropriate
		StepStrategy nextStepStrategy;
		if(gameOver){
			System.out.println("Out of lives -- Game over!");
			//screen.setNextScreen(screen);   //TODO get rid of next screen parameter - no need to keep track of!
			nextStepStrategy = new GameOverStepStrategy();
		}
		else{
			System.out.println("You lost a life.");
			//screen.setNextScreen(screen);
			nextStepStrategy = new LoseLifeStepStrategy();
		}
		screen.getTimeline().stop();
		Screen nextScreen = new Screen(nextStepStrategy, observableBundle, levelManager, gameScene, graphicsEngine);
		nextScreen.getTimeline().play();
	}
	
	private void printStepData(){
		System.out.println(levelManager.getCurrentLevel().getTimerManager());
	}
	
	/**
	 * Helper grouping all the observable logic in this class for setup.
	 */
	private void connectObservablesToLevels(){
		observableBundle.levelObservableSetup(gameScene, levelManager);
		observableBundle.setObservablesInEvents(levelManager);
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			observableBundle.attachEntityToAll(entity);
		}
	}
	
	private void setLevelStepStrategyInDieActions(){
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			for(Event event : entity.getEvents()){
				for(Action action : event.getActions()){
					if(action instanceof DieAction){
						 ((DieAction) action).setLevelStepStrategy(this);
					}
				}
			}
		}
	}
	
	private void setupGameView() {
		//TODO call graphicsEngine.setCamera() here
		graphicsEngine.setEntitiesCollection(levelManager.getCurrentLevel().getEntities());
		graphicsEngine.setScorebar(new Scorebar(levelManager));
	}
	
	public void startNextLevel(){
		// TODO start next level
	}
	
	//Temporary, for testing
	private void instantiateTestEntitesEventsActions(){
		//TEST - TODO ask Nikita, etc. how GAE does this
		Entity mario = new CharacterEntity();
		mario.setX(200);
		mario.setY(200);
		mario.setWidth(100);
		mario.setHeight(100);
		mario.setImagePath("resources/images/mario.png");
		
		Entity block = new BlockEntity();
		block.setX(200);
		block.setY(400);
		block.setWidth(100);
		block.setHeight(100);
		block.setImagePath("resources/images/block.png");
		
		TimerEvent timeRunsOut = new TimerEvent(); //trigger time currently hardcoded to 0 in constructor
		timeRunsOut.setComparsion(true, true);
		mario.addEvent(timeRunsOut);
		DieAction die = new DieAction();
		die.setEntity(mario);
		timeRunsOut.addAction(die);
		
		KeyPressEvent upPressed = new KeyPressEvent(); 
		upPressed.updateParam("Key", KeyCode.UP);
		mario.addEvent(upPressed);
		JumpSpeedAction jump = new JumpSpeedAction(); 
		jump.setEntity(mario);
		jump.updateParam("Initial Jump Speed", -15.0);
		upPressed.addAction(jump);
		
		KeyPressEvent rightPressed = new KeyPressEvent();
		rightPressed.updateParam("Key", KeyCode.RIGHT);
		mario.addEvent(rightPressed);
		WalkAction stepRight = new WalkAction();
		stepRight.setEntity(mario);
		stepRight.updateParam("Walk Speed", 5.0);
		rightPressed.addAction(stepRight);
		
		KeyPressEvent leftPressed = new KeyPressEvent();
		leftPressed.updateParam("Key", KeyCode.LEFT);
		mario.addEvent(leftPressed);
		WalkAction stepLeft = new WalkAction();
		stepLeft.setEntity(mario);
		stepLeft.updateParam("Walk Speed", -5.0);
		leftPressed.addAction(stepLeft);
		
		KeyReleaseEvent rightReleased = new KeyReleaseEvent();
		rightReleased.updateParam("Key", KeyCode.RIGHT);
		mario.addEvent(rightReleased);
		ZeroHorizontalSpeedAction stopWalking = new ZeroHorizontalSpeedAction();
		stopWalking.setEntity(mario);
		rightReleased.addAction(stopWalking);
		
		KeyReleaseEvent leftReleased = new KeyReleaseEvent();
		leftReleased.updateParam("Key", KeyCode.LEFT);
		mario.addEvent(leftReleased);
		leftReleased.addAction(stopWalking);
		
		AlwaysEvent marioAlways = new AlwaysEvent();
		mario.addEvent(marioAlways);
		MoveAction movement = new MoveAction();
		movement.setEntity(mario);
		marioAlways.addAction(movement);
		
		CollisionEvent groundCollision = new CollisionEvent();
		groundCollision.setCollision(new Collision(mario,block, CollisionSide.TOP));
		block.addEvent(groundCollision);
		ZeroVerticalSpeedAction stopFalling = new ZeroVerticalSpeedAction();
		stopFalling.setEntity(mario);
		groundCollision.addAction(stopFalling);
		
		levelManager.getCurrentLevel().getEntities().add(mario);
		levelManager.getCurrentLevel().getEntities().add(block);
	}
}