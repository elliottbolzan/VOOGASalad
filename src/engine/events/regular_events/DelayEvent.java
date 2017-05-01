package engine.events.regular_events;

import engine.Parameter;
import engine.events.Event;

public class DelayEvent extends Event {

	public DelayEvent(){
		addParam(new Parameter(getResource("DelayAmount"), double.class, 0.0));
		addParam(new Parameter(getResource("EventId"), int.class, 0.0));
	}
	@Override
	public boolean act() {
		
	}

}
