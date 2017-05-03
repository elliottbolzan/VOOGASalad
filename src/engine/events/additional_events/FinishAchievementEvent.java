package engine.events.additional_events;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import engine.events.Event;

/**
 * Make progress on an achievement and react to an achievement completing.
 * 
 * @author nikita
 */
public class FinishAchievementEvent extends Event {
	private Map<Event, Boolean> achieved;
	private boolean done;

	public FinishAchievementEvent() {
		achieved = new HashMap<Event, Boolean>();
		done = false;
	}

	@Override
	public boolean act() {
		if (!done) {
			if (achieved.keySet().size() == 0)
				getEntity().getEvents().stream().filter(s -> !s.equals(this)).forEach(s -> achieved.put(s, false));
			getEntity().getEvents().stream().filter(s -> (!s.equals(this) && s.isTriggered(true)))
					.collect(Collectors.toList()).forEach(s -> achieved.put(s, true));
			for (Event event : achieved.keySet()) {
				if (!achieved.get(event))
					return false;
			}
			done = true;
			return true;
		}
		return false;
	}
}
