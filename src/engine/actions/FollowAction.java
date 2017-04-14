package engine.actions;

import com.sun.xml.internal.stream.Entity;

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
		//this.addParam(new Parameter("Entity", Entity.class, null));
	}

	@Override
	public void act() {
		//TODO: figure out a way to set the location of an entity based on another entity
		//this.getEntity().setX(leaderEntity.getX());
		//this.getEntity().setY(leaderEntity.getY());
	}

}
