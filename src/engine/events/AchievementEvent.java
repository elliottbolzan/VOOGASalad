package engine.events;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.Event;

public class AchievementEvent extends Event{
	
	private String name;
	private String description;
	private List<Event> events;
	private Map<Event, Boolean> achieved;
	
	public AchievementEvent(String name, String description, Event...events){
		this.name = name;
		this.description = description;
		this.events = Arrays.asList(events);
		achieved = new HashMap<Event, Boolean>();
	}
	
	@Override
	public boolean act(){
		for (Event event: events){
			boolean eventAnswer = event.act();
			achieved.put(event, eventAnswer);
			if (!eventAnswer)
				return false;
		}
		return true;
	}
}
