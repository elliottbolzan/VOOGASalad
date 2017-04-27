package engine;

/**
 * Collision stores a single collision between two Entities. The information of
 * a Collision is the first and second Entity involved in the collision as well
 * as the direction from which the first Entity collided with the second Entity.
 * 
 * @author Kyle Finke Nikita Zemlevskiy
 *
 */
public class Collision implements CollisionInterface {

	private Entity firstEntity;
	private Entity secondEntity;
	private CollisionSide firstRelativeToSecond;
	private double collisionDepth;

	public Collision(Entity one, Entity two, CollisionSide side, double depth) {
		firstEntity = one;
		secondEntity = two;
		firstRelativeToSecond = side;
		collisionDepth = depth;
	}

	public CollisionSide getCollisionSide() {
		return firstRelativeToSecond;
	}
	
	public double getCollisionDepth() {
		return collisionDepth;
	}

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
	public boolean isBetween(String name1, String name2) {
		return (firstEntity.getName().equals(name1) && secondEntity.getName().equals(name2));
	}
}
