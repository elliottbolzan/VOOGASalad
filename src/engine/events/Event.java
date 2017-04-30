package engine.events;

import java.util.ArrayList;
import java.util.List;

import engine.GameObject;
import engine.Parameter;
import engine.actions.Action;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Abstract class for all events. Methods are implemented from EventInterface
 * that are common to all events.
 * 
 * @author nikita
 */
public abstract class Event extends GameObject implements EventInterface {
	private List<Action> actions;
	private SimpleIntegerProperty timesEventHasOccurred;

	public Event() {
		addParam(new Parameter("How often to trigger", int.class, 1));
		addParam(new Parameter("How many times to trigger", String.class, "Always"));
		actions = new ArrayList<Action>();
		timesEventHasOccurred = new SimpleIntegerProperty(0);
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
	 * check if this event is happening
	 * 
	 * @return if this event is happening
	 */
	@Override
	public abstract boolean act();

	/**
	 * Check whether or not to trigger the actions to fire. Depends on how many
	 * times event is set to trigger, and how often it is set to trigger.
	 * 
	 * @return whether the event is triggered or not.
	 */
	public boolean isTriggered(boolean check) {
		boolean act = act();
		if (act && !check) {
			timesEventHasOccurred.set(timesEventHasOccurred.get() + 1);
		}
		return (act && timesEventHasOccurred.get() != 0
				&& timesEventHasOccurred.get() % (int) getParam("How often to trigger") == 0 && lessThanMaxTimes());
	}

	private boolean lessThanMaxTimes() {
		if (((String) getParam("How many times to trigger")).equals("Always"))
			return true;
		else {
			try {
				return Integer.parseInt((String) getParam("How many times to trigger")) >= timesEventHasOccurred.get();
			} catch (Exception e) {
				return false;
			}
		}
	}

	/**
	 * tell all actions held by this event to act
	 */
	public void trigger() {
		actions.forEach(s -> s.act());
		// System.out.println(timesEventHasOccurred);
	}

	public SimpleIntegerProperty getNumberTimesTriggered() {
		return timesEventHasOccurred;
	}

}