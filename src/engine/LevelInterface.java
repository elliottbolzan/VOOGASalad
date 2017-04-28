package engine;

import java.util.Collection;

import engine.entities.Entity;

/**
 * @author nikita This is the interface for levels Used in the game.
 */
public interface LevelInterface {
	// external API
	/**
	 * get all the entities contained in the level.
	 * 
	 * @return a collection of all entities in the level
	 */
	Collection<Entity> getEntities(); // needed for gameplay

	/**
	 * add an entity to this level
	 * 
	 * @param entity
	 *            the entity to be added
	 */	void addEntity(Entity entity); // needed for authoring

}
