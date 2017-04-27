package engine.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import engine.GameObject;
import engine.Parameter;
import engine.actions.Action;

/**
 * Abstract class for all events. Methods are implemented from EventInterface
 * that are common to all events.
 * 
 * @author nikita
 */
public abstract class Event extends GameObject implements EventInterface {
	private List<Action> actions;
	private int timesEventHasOccurred;

	public Event() {
		super("Event");
		addParam(new Parameter("How often to trigger", int.class, 1));
		actions = new ArrayList<Action>();
		timesEventHasOccurred = 0;
	}

	@Override
	public void addAction(Action action) {
		actions.add(action);
	}

	/**
	 * 
	 * @return List of Actions associated with this Event
	 */
	public List<Action> getActions() {
		return actions;
		//return Collections.unmodifiableList(actions);
	}

	/**
	 * 
	 * @param actions
	 *            Set list of Actions that is associated with this Event
	 */
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	/**
	 * check if this event is triggered
	 * 
	 * @return if this event is triggered
	 */
	@Override
	public abstract boolean act();

	/**
	 * tell all actions held by this event to act
	 */
	public void trigger() {
		if (++timesEventHasOccurred >= (int) getParam("How often to trigger")) {
			actions.forEach(s -> s.act());
			timesEventHasOccurred = 0;
		}
	}

}