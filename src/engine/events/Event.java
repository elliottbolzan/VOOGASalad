package engine.events;

import java.util.ArrayList;
import java.util.List;

import engine.GameInfo;
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
	private int timesTriggered;

	/**
	 * Create a new event, setting the default parameters for the user to enter.
	 */
	public Event() {
		addParam(new Parameter(getResource("HowManyTimesToTrigger"), String.class, getResource("TriggerLimit")));
		addParam(new Parameter(getResource("HowOftenToTrigger"), int.class, 1));
		actions = new ArrayList<Action>();
		timesEventHasOccurred = new SimpleIntegerProperty(0);
		timesTriggered = 0;
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
	 * @param check
	 *            whether this is a check or actual update of entity
	 * @return whether the event is triggered or not.
	 */
	public boolean isTriggered(boolean check) {
		boolean act = act();
		if (act && !check)
			timesEventHasOccurred.set(timesEventHasOccurred.get() + 1);
		boolean ret = (act && timesEventHasOccurred.get() != 0
				&& timesEventHasOccurred.get() % (int) getParam(getResource("HowOftenToTrigger")) == 0
				&& lessThanMaxTimes());
		if (ret)
			timesTriggered++;
		return ret;
	}

	private boolean lessThanMaxTimes() {
		if (((String) getParam(getResource("HowManyTimesToTrigger"))).toLowerCase().equals(getResource("TriggerLimit")))
			return true;
		else {
			try {
				return Integer.parseInt((String) getParam(getResource("HowManyTimesToTrigger"))) >= timesTriggered;
			} catch (Exception e) {
				return true;
			}
		}
	}

	/**
	 * tell all actions held by this event to act
	 */
	public void trigger() {
		actions.forEach(s -> s.act());
	}

	public SimpleIntegerProperty getNumberTimesTriggered() {
		return timesEventHasOccurred;
	}

	@Override
	public GameInfo getGameInfo() {
		return getEntity().getGameInfo();
	}
}