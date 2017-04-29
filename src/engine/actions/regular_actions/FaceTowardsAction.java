package engine.actions.regular_actions;

import java.util.Collection;

import engine.Parameter;
import engine.actions.Action;
import engine.entities.Entity;

/**
 * Make this entity change its horizontal velocity component so it heads towards
 * the entity specified by the parameter.
 * @author Matthew Barbano
 *
 */

public class FaceTowardsAction extends Action {

	public FaceTowardsAction() {
		this.addParam(new Parameter("Tracked Entity", String.class, ""));
		this.addParam(new Parameter("Track Horizontal", boolean.class, true));
		this.addParam(new Parameter("Track Vertical", boolean.class, false));
		this.addParam(new Parameter("New Speed", double.class, 0.0));
	}

	@Override
	public void act() {
		if((boolean) getParam("Track Horizontal") && (boolean) getParam("Track Vertical")){
			 double xComponent = getParameterEntity("Tracked Entity").getX() - getEntity().getX();
			 double yComponent = getParameterEntity("Tracked Entity").getY() - getEntity().getY();
			 double angle = Math.atan2(yComponent, xComponent);
			 getEntity().setXSpeed(((double) getParam("New Speed"))*Math.cos(angle));
			 getEntity().setYSpeed(((double) getParam("New Speed"))*Math.sin(angle));
		}
		else if((boolean) getParam("Track Horizontal")){
			int sign = getParameterEntity("Tracked Entity").getX() < getEntity().getX() ? -1 : 1;
			getEntity().setXSpeed(sign * ((double) getParam("New Speed")));
		}
		else if((boolean) getParam("Track Vertical")){
			int sign = getParameterEntity("Tracked Entity").getY() < getEntity().getY() ? -1 : 1;
			getEntity().setYSpeed(sign * ((double) getParam("New Speed")));
		}
	}
	
	private Entity getParameterEntity(String parameterName){
		Collection<Entity> entities = this.getEntity().getGameInfo().getLevelManager().getCurrentLevel().getEntities();
		for (Entity entity : entities) {
			if (((String) getParam(parameterName)).equals(entity.getName())) {
				return entity;
			}
		}
		return null;
	}
}
