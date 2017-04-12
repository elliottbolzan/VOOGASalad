package engine;

/**
 * CollisionSide stores the possible sides that a Collision can occur between
 * two Entities.
 * 
 * @author Kyle Finke
 *
 */
public enum CollisionSide {
	RIGHT, LEFT, TOP, BOTTOM, ALL;

	private CollisionSide() {
	}

	public boolean equals(CollisionSide other) {
		return this == other || this == ALL || other == ALL;
	}
}
