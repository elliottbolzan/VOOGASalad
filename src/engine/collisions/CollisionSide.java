package engine.collisions;

import engine.entities.Entity;

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
			double[] existingCenter = getCenter(existing);
			setXY(newEntity, existing.getX() + existing.getWidth(), existingCenter[1] - newEntity.getHeight() / 2);
		}

		/**
		 * Randomly place to the right of existing entity.
		 */
		@Override
		public void placeEntityRandomly(Entity entity, Entity newEntity) {
			placeEntity(entity, newEntity);
			newEntity.setX(
					newEntity.getX() + Math.random() * entity.getGameInfo().getGraphicsEngine().getCamera().getWidth());
			newEntity.setY(Math.random()
					* (entity.getGameInfo().getGraphicsEngine().getCamera().getHeight() - newEntity.getHeight()));
		}
	},
	LEFT() {
		/**
		 * Place to the left of existing entity
		 */
		@Override
		public void placeEntity(Entity existing, Entity newEntity) {
			double[] existingCenter = getCenter(existing);
			setXY(newEntity, existing.getX() - newEntity.getWidth(), existingCenter[1] - newEntity.getHeight() / 2);
		}

		/**
		 * Randomly place to the left of existing entity.
		 */
		@Override
		public void placeEntityRandomly(Entity entity, Entity newEntity) {
			placeEntity(entity, newEntity);
			newEntity.setX(
					newEntity.getX() - Math.random() * entity.getGameInfo().getGraphicsEngine().getCamera().getWidth());
			newEntity.setY(Math.random()
					* (entity.getGameInfo().getGraphicsEngine().getCamera().getHeight() - newEntity.getHeight()));
		}
	},
	TOP() {
		/**
		 * Place above existing entity
		 */
		@Override
		public void placeEntity(Entity existing, Entity newEntity) {
			double[] existingCenter = getCenter(existing);
			setXY(newEntity, existingCenter[0] - newEntity.getWidth() / 2, existing.getY() - newEntity.getHeight());
		}

		/**
		 * Randomly place above existing entity.
		 */
		@Override
		public void placeEntityRandomly(Entity entity, Entity newEntity) {
			placeEntity(entity, newEntity);
			newEntity.setX(Math.random()
					* (entity.getGameInfo().getGraphicsEngine().getCamera().getWidth() - newEntity.getWidth()));
			newEntity.setY(newEntity.getY()
					+ Math.random() * entity.getGameInfo().getGraphicsEngine().getCamera().getHeight());
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

		/**
		 * Randomly place below existing entity.
		 */
		@Override
		public void placeEntityRandomly(Entity entity, Entity newEntity) {
			placeEntity(entity, newEntity);
			newEntity.setX(Math.random()
					* (entity.getGameInfo().getGraphicsEngine().getCamera().getWidth() - newEntity.getWidth()));
			newEntity.setY(newEntity.getY()
					- Math.random() * entity.getGameInfo().getGraphicsEngine().getCamera().getHeight());
		}
	},
	ALL() {
		/**
		 * Place in the middle of existing Entity
		 */
		@Override
		public void placeEntity(Entity existing, Entity newEntity) {
			double[] existingCenter = getCenter(existing);
			setXY(newEntity, existingCenter[0] - newEntity.getWidth() / 2,
					existingCenter[1] - newEntity.getHeight() / 2);
		}

		/**
		 * Place randomly on the screen
		 */
		@Override
		public void placeEntityRandomly(Entity entity, Entity newEntity) {
			newEntity.setX(Math.random()
					* (entity.getGameInfo().getGraphicsEngine().getCamera().getWidth() - newEntity.getWidth()));
			newEntity.setY(Math.random()
					* (entity.getGameInfo().getGraphicsEngine().getCamera().getHeight() - newEntity.getHeight()));
		}
	};

	private static double[] getCenter(Entity entity) {
		double[] answer = new double[2];
		answer[0] = entity.getX() + entity.getWidth() / 2;
		answer[1] = entity.getY() + entity.getHeight() / 2;
		return answer;
	}

	private static void setXY(Entity entity, double x, double y) {
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

	/**
	 * Place a new entity in the game in a random position relative to the
	 * existing side, based on which side is requested. The side which the user
	 * requested to place the entity on will be honored
	 * 
	 * @param existing
	 *            the existing entity
	 * @param newEntity
	 *            the new entity to be placed
	 */
	public abstract void placeEntityRandomly(Entity entity, Entity newEntity);
}
