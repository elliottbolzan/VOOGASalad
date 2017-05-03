package engine.game.eventobserver;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import engine.entities.Entity;
import exceptions.ObservableException;

/**
 * Observer superclass for the Observable Design Pattern for detecting and responding to Events.
 * Subclasses contain Lists of ObservableEntities that will listen to the
 * corresponding Observer. Class is abstract since you should only be able to
 * instantiate specific subclasses of EventObserver. Assumes that the argument of attach()
 * refers to an existing Entity in the game, and that of detach() refers to an Entity
 * in the "observers" List. Dependencies are Entity and ObservableException.
 * 
 * Example of use:
 * <pre>EventObservable observable = new TimerObservable();
 * ...
 * for(Entity entity : levelManager.getCurrentLevel().getEntities()){
 * 		observable.attach(entity);
 * }
 * ...
 * observable.updateObservers();
 * ...
 * for(Entity entity : levelManager.getCurrentLevel().getEntities()){
 * 		observable.detach(entity);
 * }
 *</pre>
 * 
 * @author Matthew Barbano
 *
 */
public abstract class EventObservable {
	private List<Entity> observers;
	private transient ResourceBundle resources = ResourceBundle.getBundle("resources/Strings");

	public EventObservable() {
		observers = new ArrayList<>();
	}

	/**
	 * Engine External API. Adds toAttach to the appropriate List(s) of
	 * ObservableEntities that will update upon calling updateObservables().
	 * Assumes that "toDetach" must be the same
	 * object in memory as originally added to the list.
	 * 
	 * @param toAttach the Entity to attach
	 */
	public void attach(Entity toAttach) {
		observers.add(toAttach);
	}

	/**
	 * Engine External API. Removes toDetach from all Lists of
	 * ObservableEntities that will update upon calling updateObservables().
	 * Assumes that observers.contains(toDetach) is true
	 * 
	 * @param toDetach the Entity to remove from observers
	 * @throws ObservableException if toDetach is not in observers
	 */
	public void detach(Entity toDetach) {
		try {
			if(observers.contains(toDetach)){
				observers.remove(observers.indexOf(toDetach));
			}
		} catch (Exception e) {
			throw new ObservableException(resources.getString("EntityNotAttached"));
		}
	}

	/**
	 * Engine External API. Assumption is that it is called on every iteration
	 * of the game loop during game play. Takes whatever action is appropriate
	 * on each iteration of the game loop specific to each observable subclass.
	 */
	public abstract void updateObservers();
 
	/**
	 * Returns the observers List
	 * @return observers
	 */
	protected List<Entity> getObservers() {
		return observers;
	}
}
