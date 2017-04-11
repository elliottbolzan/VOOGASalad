package engine;

import java.util.ArrayList;
import java.util.List;

public abstract class Event extends GameObject implements EventInterface {
	
	private List<Action> actions;

	public Event(){
		super("Event");
		actions = new ArrayList<Action>();
	}

	@Override
	public void addAction(Action action) {
		actions.add(action);
	}
	
	public List<Action> getActions() {
		return actions;
	}
	
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	@Override
	public abstract boolean act();

	public void trigger() {
		for (Action action : actions)
			action.act();
	}
	
}