package engine.game.eventobserver;

import java.util.ArrayList;
import java.util.List;

import engine.Collision;
import engine.CollisionSide;
import engine.Entity;

/**
 * Part of the Observable Design Pattern for detecting if collisions occur
 * between Entities. Collisions that are detected are stored as a Collision in a
 * list of Collisions.
 * 
 * @author Kyle Finke
 * @author Matthew Barbano
 *
 */
public class CollisionObservable extends EventObservable {

	private List<Collision> collisions = new ArrayList<>();

	public CollisionObservable() {
		super();
	}

	private CollisionSide collisionSide(Entity entityOne, Entity entityTwo) {
		if (isHorizontalCollision(entityOne, entityTwo)) {
			if (entityOne.getX() < entityTwo.getX()) {
				return CollisionSide.RIGHT;
			}
			return CollisionSide.LEFT;
		}
		if (entityOne.getY() + entityOne.getHeight() < entityTwo.getY() + entityTwo.getHeight()) {
			return CollisionSide.TOP;
		}
		return CollisionSide.BOTTOM;

	}

	private boolean isHorizontalCollision(Entity entityOne, Entity entityTwo) {
		return getIntersectionHeight(entityOne, entityTwo) > getIntersectionWidth(entityOne, entityTwo);
	}

	private double getIntersectionWidth(Entity entityOne, Entity entityTwo) {
		if (entityOne.getX() + entityOne.getWidth() < entityTwo.getX() + entityTwo.getWidth()) {
			if (entityOne.getX() < entityTwo.getX()) {
				return (entityOne.getX() + entityOne.getWidth()) - entityTwo.getX();
			}
			return entityOne.getWidth();
		}
		return (entityTwo.getX() + entityTwo.getWidth()) - entityOne.getX();
	}

	private double getIntersectionHeight(Entity entityOne, Entity entityTwo) {
		if (entityOne.getY() < entityTwo.getY()) {
			if (entityOne.getY() + entityOne.getHeight() < entityTwo.getY() + entityTwo.getHeight()) {
				return entityOne.getY() - (entityTwo.getY() + entityTwo.getHeight());
			}
			return entityOne.getHeight();
		}
		return entityTwo.getY() - (entityOne.getY() + entityOne.getHeight());
	}

	/**
	 * Checks all entities in the current level for collisions. If a Collision
	 * is detected, it is added to a list of Collisions.
	 */
	@Override
	public void updateObservers() {
		for (Entity first : getObservers()) {
			for (Entity second : getObservers()) {
				if (first != second && isCollision(first, second)) {
					System.out.println("Added collision between " + first.getName() + " and " + second.getName());
					collisions.add(new Collision(first, second, collisionSide(first, second)));
				}
			}
		}
	}

	/**
	 * 
	 * @return list of collisions that occurred between observed Entities
	 */
	public List<Collision> getCollisions() {
		return collisions;
	}

	private boolean isCollision(Entity first, Entity second) {
		return !(first.getZ() != second.getZ() || first.getX() + first.getWidth() < second.getX()
				|| second.getX() + second.getWidth() < first.getX() || first.getY() + first.getHeight() < second.getY()
				|| second.getY() + second.getHeight() < first.getY());
	}
}