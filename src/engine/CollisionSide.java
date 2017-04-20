package engine;

/**
 * CollisionSide stores the possible sides that a Collision can occur between
 * two Entities.
 * 
 * @author Kyle Finke Nikita Zemlevskiy
 *
 */
public enum CollisionSide {
	RIGHT() {
		/**
		 * Place to the right of existing entity
		 */
		@Override
		public void placeEntity(Entity existing, Entity newEntity) {
			double [] existingCenter = getCenter(existing);
			setXY(newEntity, existing.getX() + existing.getWidth(), existingCenter[1] - newEntity.getHeight()/2);
		}
	},
	LEFT() {
		/**
		 * Place to the left of existing entity
		 */
		@Override
		public void placeEntity(Entity existing, Entity newEntity) {
			double [] existingCenter = getCenter(existing);
			setXY(newEntity, existing.getX() - newEntity.getWidth(), existingCenter[1] - newEntity.getHeight()/2);
		}
	},
	TOP() {
		/**
		 * Place above existing entity
		 */
		@Override
		public void placeEntity(Entity existing, Entity newEntity) {
			double [] existingCenter = getCenter(existing);
			setXY(newEntity, existingCenter[0] - newEntity.getWidth() / 2, existing.getY() - newEntity.getHeight());
		}
	},
	BOTTOM() {
		/**
		 * Place under existing entity
		 */
		@Override
		public void placeEntity(Entity existing, Entity newEntity) {
			double[] existingCenter = getCenter(existing);
			setXY(newEntity, existingCenter[0] - newEntity.getWidth() / 2, existing.getY() + existing.getHeight());
		}
	},
	ALL() {
		/**
		 * Place in the middle of existing Entity
		 */
		@Override
		public void placeEntity(Entity existing, Entity newEntity) {
			double[] existingCenter = getCenter(existing);
			setXY(newEntity, existingCenter[0] - newEntity.getWidth() / 2, existingCenter[1] - newEntity.getHeight() / 2);
		}
	};

	private static double[] getCenter(Entity entity) {
		double[] answer = new double[2];
		answer[0] = entity.getX() + entity.getWidth() / 2;
		answer[1] = entity.getY() + entity.getHeight() / 2;
		return answer;
	}
	
	private static void setXY(Entity entity, double x, double y){
		entity.setX(x);
		entity.setY(y);
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

	/**
	 * Place a new entity in the game relative to the existing side, based on
	 * which side is requested.
	 * 
	 * @param existing
	 *            the existing entity
	 * @param newEntity
	 *            the new entity to be placed
	 */
	public abstract void placeEntity(Entity existing, Entity newEntity);
}
