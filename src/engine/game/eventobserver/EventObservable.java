package engine.game.eventobserver;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;

/**
 * Part of the Observable Design Pattern for detecting and responding to Events.
 * Subclasses contain Lists of ObservableEntities that will listen to the
 * corresponding Observer. Class is abstract since you should only be able to
 * instantiate specific TYPES of EventObserver.
 * 
 * @author Matthew Barbano
 *
 */
public abstract class EventObservable {
	private List<Entity> observers;

	public EventObservable() {
		observers = new ArrayList<>();
	}

	/**
	 * Engine External API. Adds toAttach to the appropriate List(s) of
	 * ObservableEntities that will update upon calling updateObservables().
	 * 
	 * To other programmers on team - Careful: "toDetach" must be the same
	 * object in memory as originally added to the list If you want me to change
	 * this behavior, let me know.
	 */
	public void attach(Entity toAttach) {
		observers.add(toAttach);
	}

	/**
	 * Engine External API. Removes toDetach from all Lists of
	 * ObservableEntities that will update upon calling updateObservables().
	 */
	public void detach(Entity toDetach) {
		observers.remove(observers.indexOf(toDetach));
	}

	/**
	 * Engine External API. Iterates through the List of ObservableEntities in
	 * the appropriate subclass and calls their update() method.
	 */
	public abstract void updateObservers();
	
	protected List<Entity> getObservers(){
		return observers;
	}
}
