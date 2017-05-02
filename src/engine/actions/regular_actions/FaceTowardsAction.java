package engine.actions.regular_actions;

import java.util.Collection;

import engine.Parameter;
import engine.actions.Action;
import engine.entities.Entity;

/**
 * Make this entity change its horizontal velocity component so it heads towards
 * the entity specified by the parameter.
 * 
 * @author Matthew Barbano
 *
 */

public class FaceTowardsAction extends Action {

	public FaceTowardsAction() {
		this.addParam(new Parameter(getResource("TrackedEntity"), String.class, ""));
		this.addParam(new Parameter(getResource("TrackHorizontal"), boolean.class, true));
		this.addParam(new Parameter(getResource("TrackVertical"), boolean.class, false));
		this.addParam(new Parameter(getResource("NewSpeed"), double.class, 0.0));
	}

	@Override
	public void act() {
		if (getParameterEntity(getResource("TrackedEntity")) == null)
			return;
		if ((boolean) getParam(getResource("TrackHorizontal")) && (boolean) getParam(getResource("TrackVertical"))) {
			double xComponent = getParameterEntity(getResource("TrackedEntity")).getX() - getEntity().getX();
			double yComponent = getParameterEntity(getResource("TrackedEntity")).getY() - getEntity().getY();
			double angle = Math.atan2(yComponent, xComponent);
			getEntity().setXSpeed(((double) getParam(getResource("NewSpeed"))) * Math.cos(angle));
			getEntity().setYSpeed(((double) getParam(getResource("NewSpeed"))) * Math.sin(angle));
		} else if ((boolean) getParam(getResource("TrackHorizontal"))) {
			int sign = getParameterEntity(getResource("TrackedEntity")).getX() < getEntity().getX() ? -1 : 1;
			getEntity().setXSpeed(sign * ((double) getParam(getResource("NewSpeed"))));
		} else if ((boolean) getParam(getResource("TrackVertical"))) {
			int sign = getParameterEntity(getResource("TrackedEntity")).getY() < getEntity().getY() ? -1 : 1;
			getEntity().setYSpeed(sign * ((double) getParam(getResource("NewSpeed"))));
		}
	}

	private Entity getParameterEntity(String parameterName) {
		Collection<Entity> entities = this.getEntity().getGameInfo().getLevelManager().getCurrentLevel().getEntities();
		for (Entity entity : entities) {
			if (((String) getParam(parameterName)).equals(entity.getName())) {
				return entity;
			}
		}
		return null;
	}
}
