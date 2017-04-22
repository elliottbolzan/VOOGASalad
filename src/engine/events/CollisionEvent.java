package engine.events;
import engine.Event;
import engine.Parameter;
import engine.Collision;
import engine.CollisionSide;
/**
 * Stores a Collision that is associated with a certain Entity. Whenever that
 * collision occurs, the Actions associated with the CollisionEvent will run.
 * 
 * @author Kyle Finke
 *
 */
public class CollisionEvent extends Event {
	private Collision collision;
	public CollisionEvent() {
		addParam(new Parameter("Entity", String.class, ""));
		this.collision = new Collision(null, null, CollisionSide.ALL);
	}
	/**
	 * Sets the primary Collision associated with the CollisionEvent for the
	 * associated Entity.
	 * 
	 * @param collision
	 */
	public void setCollision(Collision collision) {
		this.collision = collision;
	}
	/**
	 * Checks the list of Collisions for the current step of the game against
	 * the Collision contained in this CollisionEvent. If any Collision in the
	 * list of all Collisions is equal to the one contained in this
	 * CollisionEvent, the event returns true. Otherwise, it returns false.
	 */
	@Override
	public boolean act() {
		for (Collision collision : getGameInfo().getObservableBundle().getCollisionObservable().getCollisions()) {
			if (collision.isBetween(getEntity().getName(), (String) getParam("Entity"))
					&& collision.getCollisionSide().equals(this.collision.getCollisionSide())) {
				return true;
			}
		}
		return false;
	}
}