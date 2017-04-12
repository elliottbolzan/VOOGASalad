package engine.events;

import engine.Event;
import engine.Parameter;
import engine.Collision;
import engine.CollisionEventInterface;
import engine.CollisionSide;

public class CollisionEvent extends Event implements CollisionEventInterface {

	private Collision collision;

	public CollisionEvent() {
		addParam(new Parameter("Entity", String.class, ""));
		this.collision = new Collision(null, null, CollisionSide.ALL);
	}

	public void setCollision(Collision collision) {
		this.collision = collision;
	}

	@Override
	public boolean act() {
		for (Collision collision : getGameInfo().getObservableBundle().getCollisionObservable().getCollisions()) {
			System.out.println("Collision" + collision);
			System.out.println("This Collision" + this.collision);
			if (collision.isBetween(getEntity().getName(), (String) getParam("Entity"))
					&& collision.getCollisionSide().equals(this.collision.getCollisionSide()))
				return true;
		}
		return false;
	}

}