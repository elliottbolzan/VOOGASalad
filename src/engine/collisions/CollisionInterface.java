package engine.collisions;

/**
 * @author nikita Interface for Collision objects. These are made when a
 *         collision between two entities occurs, or when a user requests a
 *         CollisionEvent in the authoring environment
 */
public interface CollisionInterface {

	/**
	 * Check whether this collision is between two entities with the respective
	 * names
	 * 
	 * @param name1
	 *            name of the first entity colliding
	 * @param name2
	 *            name of the second entity colliding
	 * @return whether the names given match the names of the entities in this
	 *         collision.
	 */
	boolean isBetween(String name1, String name2);
}
