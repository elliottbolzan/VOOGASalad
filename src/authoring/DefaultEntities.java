package authoring;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import engine.Entity;
import engine.entities.CharacterEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Elliott Bolzan
 *
 */
public class DefaultEntities {

	private Workspace workspace;
	private ObservableList<Entity> entities;
	private Entity selected;

	/**
	 * 
	 */
	public DefaultEntities(Workspace workspace) {
		this.workspace = workspace;
		this.entities = FXCollections.observableArrayList(new ArrayList<Entity>());
		add(new CharacterEntity());
	}

	public ObservableList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities.clear();
		this.entities.addAll(entities);
	}

	public Entity getSelectedEntity() {
		return selected;
	}

	public void setSelectedEntity(Entity entity) {
		this.selected = entity;
	}

	public void add(Entity entity) {
		entities.add(entity);
		updateModel();
	}

	public void remove(Entity entity) {
		entities.remove(entity);
		updateModel();
	}

	public List<String> getNames() {
		return entities.stream().map(Entity::getName).collect(Collectors.toList());
	}

	private void updateModel() {
		workspace.getGame().setDefaults(new ArrayList<Entity>(entities));
	}

}
