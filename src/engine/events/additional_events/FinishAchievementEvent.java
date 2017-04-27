package engine.events.additional_events;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import engine.events.Event;

public class FinishAchievementEvent extends Event {
	private Map<Event, Boolean> achieved;

	public FinishAchievementEvent() {
		achieved = new HashMap<Event, Boolean>();
	}

	@Override
	public boolean act() {
		if (achieved.keySet().size() == 0)
			getEntity().getEvents().stream().filter(s -> !s.equals(this)).forEach(s -> achieved.put(s, false));
		getEntity().getEvents().stream().filter(s -> (!s.equals(this) && s.isTriggered(true))).collect(Collectors.toList())
				.forEach(s -> achieved.put(s, true));
		for (Event event : achieved.keySet()) {
			if (!achieved.get(event))
				return false;
		}
		System.out.println("Finished achievement");
		return true;
	}
}
