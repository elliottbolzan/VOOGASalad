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

	/**
	 * Determines whether this CollisionSide is equal to the other CollisionSide
	 * 
	 * @param other
	 *            CollisionSide to which this is compared
	 * @return true if this == other, and false otherwise
	 */
	public boolean equals(CollisionSide other) {
		return this == other || this == ALL || other == ALL;
	}
}
