package engine.game.gameloop;

import engine.Entity;
import engine.Event;
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
	
	public ObservableBundle(){
		inputObservable = new InputObservable();
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
	
	/**
	 * Put observable action methods in here to avoid using getters and setters, thus reducing dependencies.
	 * @param levelManager
	 */
	public void levelObservableSetup(Scene gameScene, LevelManager levelManager){
		inputObservable.setupInputListeners(gameScene);
		timerObservable.attachCurrentLevelTimerManager(levelManager.getCurrentLevel().getTimerManager());
	}
	
	/*
	public void setObservablesInEvents(LevelManager levelManager){
		for (Entity entity : levelManager.getCurrentLevel().getEntities()) {
			for (Event event : entity.getEvents()) {
				if (event instanceof KeyPressEvent || event instanceof KeyReleaseEvent) {
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
	}
	*/
	
	public void updateObservers(){
		inputObservable.updateObservers();
		collisionObservable.updateObservers();
		timerObservable.updateObservers(); 
	}
}
