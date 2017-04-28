package engine.events.regular_events;

import engine.Parameter;
import engine.entities.Entity;
import engine.events.Event;

/**
 * Checks the distance between the Entity associated with this Event and the
 * Entity with the name entered in the Entity Parameter.
 * 
 * @author Kyle Finke
 *
 */
public class EntityDistanceEvent extends Event {

	public EntityDistanceEvent() {
		addParam(new Parameter("Entity", String.class, ""));
		addParam(new Parameter("Distance", Double.class, ""));
		addParam(new Parameter("Less Than", Boolean.class, ""));
	}

	@Override
	public boolean act() {
		for (Entity entity : getGameInfo().getLevelManager().getCurrentLevel().getEntities()) {
			if (((String) getParam("Entity")).equals(entity.getName())) {
				if(distanceBetween(getEntity(),entity) < (Double) getParam("Distance")){
					return (Boolean)getParam("Less Than");
				}
			}
		}
		return false;
	}

	private double distanceBetween(Entity entityOne, Entity entityTwo) {
		double entityOneCenterX = entityOne.getX() + entityOne.getHeight() / 2;
		double entityOneCenterY = entityOne.getY() + entityOne.getWidth() / 2;
		double entityTwoCenterX = entityTwo.getX() + entityTwo.getHeight() / 2;
		double entityTwoCenterY = entityTwo.getY() + entityTwo.getWidth() / 2;
		return Math.sqrt(
				Math.pow(entityOneCenterX - entityTwoCenterX, 2) + Math.pow(entityOneCenterY - entityTwoCenterY, 2));
	}

}
