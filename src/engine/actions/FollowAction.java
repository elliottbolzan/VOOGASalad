package engine.actions;

import engine.Entity;
import engine.Parameter;

import java.util.Collection;

import engine.Action;

/**
 * This Action will be used for a following camera. Every frame it should set the location
 * of it's Entity (the camera) based on a leader Entity (the character).
 * 
 * @author Jay Doherty
 *
 */
public class FollowAction extends Action {

	public FollowAction() {
		this.addParam(new Parameter("Leader Entity", String.class, ""));
	}

	@Override
	public void act() {
		Collection<Entity> entities = this.getEntity().getGameInfo().getLevelManager().getCurrentLevel().getEntities();
		for(Entity entity : entities) {
			if( ((String) getParam("Leader Entity")).equals(entity.getName()) ) {
				this.getEntity().setX(entity.getX() + (entity.getWidth()/2) - (this.getEntity().getWidth()/2));
				this.getEntity().setY(entity.getY() + (entity.getHeight()/2) - (this.getEntity().getHeight()/2));
			}
		}
	}
}
