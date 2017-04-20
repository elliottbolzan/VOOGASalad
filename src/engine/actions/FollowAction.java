package engine.actions;

import engine.Entity;
import engine.Parameter;
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
		//TODO: figure out how to have an Entity parameter? Not sure if this would work
		this.addParam(new Parameter("Leader Entity", Entity.class, null));
	}

	@Override
	public void act() {
		this.getEntity().setX(((Entity) getParam("Leader Entity")).getX());
		this.getEntity().setY(((Entity) getParam("Leader Entity")).getY());
	}

}
