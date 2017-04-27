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
	 * check if this event is triggered
	 * 
	 * @return if this event is triggered
	 */
	@Override
	public abstract boolean act();

	public boolean isTriggered(boolean check) {
		if (act() && !check){
			timesEventHasOccurred.set(timesEventHasOccurred.get() + 1);
		}
		return (act() && timesEventHasOccurred.get() != 0 && timesEventHasOccurred.get() % (int) getParam("How often to trigger") == 0);
	}

	/**
	 * tell all actions held by this event to act
	 */
	public void trigger() {/*
							 * timesEventHasOccurred.set(timesEventHasOccurred.
							 * get()+1); actions.forEach(s -> s.act()); if
							 * (timesEventHasOccurred.get() >= (int)
							 * getParam("How often to trigger")) {
							 * timesEventHasOccurred.set(0); }
							 */
		actions.forEach(s -> s.act());
	}

	public SimpleIntegerProperty getNumberTimesTriggered() {
		return timesEventHasOccurred;
	}

}