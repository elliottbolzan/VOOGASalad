package engine.game.gameloop;

import engine.Entity;
import engine.Event;
import engine.GameInfo;
import engine.events.CollisionEvent;
import engine.events.KeyPressEvent;
import engine.events.KeyReleaseEvent;
import engine.events.TimerEvent;
import engine.game.LevelManager;
import engine.game.eventobserver.CollisionObservable;
import engine.game.eventobserver.InputObservable;
import engine.game.eventobserver.TimerObservable;
import javafx.scene.Scene;

/**
 * Package the observables together for easy naming and passing as parameter.
 * @author Matthew Barbano
 *
 */
public class ObservableBundle {
	private InputObservable inputObservable;
	private CollisionObservable collisionObservable;
	private TimerObservable timerObservable;
	
	public ObservableBundle(Scene gameScene){
		inputObservable = new InputObservable(gameScene);
		collisionObservable = new CollisionObservable();
		timerObservable = new TimerObservable();
	}

	public InputObservable getInputObservable() {
		return inputObservable;
	}

	public void setInputObservable(InputObservable inputObservable) {
		this.inputObservable = inputObservable;
	}

	public CollisionObservable getCollisionObservable() {
		return collisionObservable;
	}

	public void setCollisionObservable(CollisionObservable collisionObservable) {
		this.collisionObservable = collisionObservable;
	}

	public TimerObservable getTimerObservable() {
		return timerObservable;
	}

	public void setTimerObservable(TimerObservable timerObservable) {
		this.timerObservable = timerObservable;
	}
	
	public void attachEntityToAll(Entity entity){
		inputObservable.attach(entity);
		collisionObservable.attach(entity);
		timerObservable.attach(entity);
	}
	
	public void detachEntityFromAll(Entity entity){
		inputObservable.detach(entity);
		collisionObservable.detach(entity);
		timerObservable.detach(entity);
	}
	
	/**
	 * Put observable action methods in here to avoid using getters and setters, thus reducing dependencies.
	 * @param levelManager
	 */
	public void levelObservableSetup(GameInfo gameInfo){
		inputObservable.setupInputListeners();
		timerObservable.attachCurrentLevelTimerManager(gameInfo.getScorebar().getTimerManager());
	}
	
	public void updateObservers(){
		inputObservable.updateObservers();
		collisionObservable.updateObservers();
		timerObservable.updateObservers(); 
	}
}
