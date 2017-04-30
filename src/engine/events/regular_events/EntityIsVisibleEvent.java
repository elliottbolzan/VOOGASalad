package engine.events.regular_events;

import java.util.Collection;

import engine.Parameter;
import engine.entities.Entity;
import engine.events.Event;

public class EntityIsVisibleEvent extends Event {

	public EntityIsVisibleEvent() {
		addParam(new Parameter(getResource("Entity"), String.class, ""));
	}

	@Override
	public boolean act() {
		Collection<Entity> entities = this.getEntity().getGameInfo().getLevelManager().getCurrentLevel().getEntities();
		for (Entity entity : entities) {
			if (((String) getParam(getResource("Entity"))).equals(entity.getName())) {
				return getEntity().getIsVisible();
			}
		}
		return false;
	}
}