package engine;

import java.util.Arrays;
import java.util.List;

public class Achievement {
	
	private String name;
	private String description;
	private List<Event> events;

	public Achievement(String name, String description, Event...events){
		this.name = name;
		this.description = description;
		this.events = Arrays.asList(events);
	}
}
