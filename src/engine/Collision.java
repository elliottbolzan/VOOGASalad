package engine;

/**
 * Collision stores a single collision between two Entities. The information of
 * a Collision is the first and second Entity involved in the collision as well
 * as the direction from which the first Entity collided with the second Entity.
 * 
 * @author Kyle Finke
 *
 */
public class Collision implements CollisionInterface {

	private Entity firstEntity;
	private Entity secondEntity;
	private CollisionSide firstRelativeToSecond;
	private String secondName;

	public Collision(Entity one, Entity two, CollisionSide side) {
		firstEntity = one;
		secondEntity = two;
		firstRelativeToSecond = side;
		secondName = secondEntity == null ? "" : secondEntity.getName();
	}

	/**
	 * @return boolean result that is true if two Collisions contain the same
	 *         Entities and CollisionSide and false otherwise.
	 */
	/*@Override
	public boolean equals(Collision other) {
		// TODO find another way to compare collisions without using instanceof
		return (other instanceof Collision && (checkFirstSecond(other) || checkNames(other))
				&& firstRelativeToSecond.equals(other.getCollisionSide()));
	}
	*/
	/*
	 * private boolean checkFirstSecond(Collision other) { return
	 * ((firstEntity.equals(other.getFirstEntity()) &&
	 * secondEntity.equals(other.getSecondEntity())) ||
	 * (secondEntity.equals(other.getFirstEntity()) &&
	 * firstEntity.equals(other.getSecondEntity()))); }
	 */

	/*
	 * private boolean checkNames(Collision other) {
	 * System.out.println("SECOND NAME: " + secondName);
	 * System.out.println("OTHER: " + other);
	 * System.out.println("OTHER.first entity: " + other.getFirstEntity());
	 * System.out.println("other.second entity: " + other.getSecondEntity());
	 * System.out.println(" -------------------------"); return (secondName !=
	 * null && other.getFirstEntity() != null && other.getSecondEntity() != null
	 * && ((firstEntity.equals(other.getFirstEntity()) &&
	 * secondName.equals(other.getSecondEntity().getName())) ||
	 * (firstEntity.equals(other.getSecondEntity()) &&
	 * secondName.equals(other.getFirstEntity().getName())))); }
	 */
	/*
	 * private boolean checkNames(Collision other){ return }
	 */

	public CollisionSide getCollisionSide() {
		return firstRelativeToSecond;
	}

	public Entity getFirstEntity() {
		return firstEntity;
	}

	public Entity getSecondEntity() {
		return secondEntity;
	}

	public void setFirstEntity(Entity entity) {
		firstEntity = entity;
	}

	public void setSecondName(String name) {
		this.secondName = name;
	}

	public boolean isBetween(String name, String param) {
		return (firstEntity.getName().equals(name) && secondEntity.getName().equals(param))
				|| (firstEntity.getName().equals(param) && secondEntity.getName().equals(name));
	}
}
