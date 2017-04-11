package engine.events;

import engine.Event;
import engine.Parameter;
import engine.game.eventobserver.CollisionObservable;
import engine.Collision;
import engine.CollisionEventInterface;

public class CollisionEvent extends Event implements CollisionEventInterface {

	private Collision collision;

	public CollisionEvent() {
		addParam(new Parameter("Entity", String.class, ""));
	}

	public void setCollision(Collision collision) {
		this.collision = collision;
	}

	@Override
	public Collision getCollision() {
		return collision;
	}

	@Override
	public boolean act() {
		// collision.setFirstEntity(getEntity()); //TODO: should this just get
		// removed? Currently it doesnt work
		// collision.setSecondName((String)getParam("Entity"));
		for (Collision collision : getGameInfo().getObservableBundle().getCollisionObservable().getCollisions()) {
			if (collision.equals(this.collision))
				return true;
		}
		return false;

	}
}
