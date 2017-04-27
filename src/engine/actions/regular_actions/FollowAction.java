package engine.actions.regular_actions;

import engine.Entity;
import engine.Parameter;

import java.util.Collection;

import engine.Action;

/**
 * This Action will be used for a following camera. Every frame it should set the location
 * of its Entity (the camera) based on a leader Entity (the character).
 * 
 * @author Jay Doherty
 *
 */
public class FollowAction extends Action {

	public FollowAction() {
		this.addParam(new Parameter("Leader Entity", String.class, ""));
		this.addParam(new Parameter("Track Horizontally", boolean.class, true));
		this.addParam(new Parameter("Track Vertically", boolean.class, true));
	}

	@Override
	public void act() {
		Collection<Entity> entities = this.getEntity().getGameInfo().getLevelManager().getCurrentLevel().getEntities();
		for(Entity entity : entities) {
			if( ((String) getParam("Leader Entity")).equals(entity.getName()) ) {
				if((boolean) getParam("Track Horizontally")) {
					this.getEntity().setX(entity.getX() + (entity.getWidth()/2) - (this.getEntity().getWidth()/2));
				}
				if((boolean) getParam("Track Vertically")) {
					this.getEntity().setY(entity.getY() + (entity.getHeight()/2) - (this.getEntity().getHeight()/2));
				}
			}
		}
	}
}
