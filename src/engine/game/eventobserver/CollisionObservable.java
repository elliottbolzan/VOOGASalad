package engine.game.eventobserver;

import java.util.ArrayList;
import java.util.List;

import engine.collisions.Collision;
import engine.collisions.CollisionSide;
import engine.entities.Entity;

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

	/**
	 * 
	 * @return list of collisions that occurred between observed Entities
	 */
	public List<Collision> getCollisions() {
		return collisions;
	}

	private CollisionSide collisionSide(Entity entityOne, Entity entityTwo) {
		if (isHorizontalCollision(entityOne, entityTwo)) {
			if (entityOne.getX() < entityTwo.getX()) {
				return CollisionSide.RIGHT;
			}
			return CollisionSide.LEFT;
		}
		if (entityOne.getY() < entityTwo.getY()) {
			return CollisionSide.BOTTOM;
		}
		return CollisionSide.TOP;
	}

	private double collisionDepth(Entity entityOne, Entity entityTwo) {
		if (isHorizontalCollision(entityOne, entityTwo)) {
			return getIntersectionWidth(entityOne, entityTwo);
		} else {
			return getIntersectionHeight(entityOne, entityTwo);
		}
	}

	private boolean isHorizontalCollision(Entity entityOne, Entity entityTwo) {
		return getIntersectionHeight(entityOne, entityTwo) > getIntersectionWidth(entityOne, entityTwo);
	}

	private double getIntersectionWidth(Entity entityOne, Entity entityTwo) {
		return intersectionSize(entityOne.getX(), entityOne.getX() + entityOne.getWidth(), entityTwo.getX(),
				entityTwo.getX() + entityTwo.getWidth());
	}

	private double getIntersectionHeight(Entity entityOne, Entity entityTwo) {
		return intersectionSize(entityOne.getY(), entityOne.getY() + entityOne.getHeight(), entityTwo.getY(),
				entityTwo.getY() + entityTwo.getHeight());
	}

	private double intersectionSize(double sideOneMin, double sideOneMax, double sideTwoMin, double sideTwoMax) {
		if (sideOneMin < sideTwoMin) {
			if (sideOneMax < sideTwoMax) {
				return sideOneMax - sideTwoMin;
			}
			return sideTwoMax - sideTwoMin;
		} else if (sideOneMax < sideTwoMax) {
			return sideOneMax - sideOneMin;
		}
		return sideTwoMax - sideOneMin;
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
					collisions.add(
							new Collision(first, second, collisionSide(first, second), collisionDepth(first, second)));
				}
			}
		}
	}

	private boolean isCollision(Entity first, Entity second) {
		return (first.getZ() == second.getZ()) && first.getIsVisible() && second.getIsVisible()
				&& !(first.getX() + first.getWidth() < second.getX() || second.getX() + second.getWidth() < first.getX()
						|| first.getY() + first.getHeight() < second.getY()
						|| second.getY() + second.getHeight() < first.getY());
	}
}