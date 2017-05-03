package engine.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import engine.LevelInterface;
import engine.entities.Entity;
import engine.entities.entities.BackgroundEntity;
import engine.entities.entities.CameraEntity;
import engine.game.selectiongroup.Selectable;

/**
 * Manages a single Level. Most critically, contains a List (or possibly another
 * variety of Collection) of Entities present in that Level. Handles the Game
 * Time (see TimerManager) for timed levels. More to be added soon.
 * 
 * @author Matthew Barbano
 *
 */
public class Level implements LevelInterface, Selectable {
	private List<Entity> entities;
	private CameraEntity camera;
	private BackgroundEntity background;

	public Level() {
		entities = new ArrayList<>();
		camera = new CameraEntity();
		background = new BackgroundEntity();
	}

	/**
	 * External Engine API. Needed for gameplay.
	 * 
	 * @return
	 */
	public Collection<Entity> getEntities() {
		return entities;
	}

	/**
	 * External Engine API. Needed for authoring (and possibly gameplay).
	 * 
	 * @param entity
	 * @return
	 */
	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	/**
	 * External Engine API. Needed for authoring (and possibly gameplay).
	 * 
	 * @param entity
	 */
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}

	public CameraEntity getCamera() {
		return this.camera;
	}

	public void setCamera(CameraEntity camera) {
		this.camera = camera;
	}

	public BackgroundEntity getBackground() {
		return this.background;
	}

	public void setBackground(BackgroundEntity background) {
		this.background = background;
	}

	public void addEntities(List<Entity> entities) {
		this.entities.addAll(entities);
	}
}
