package authoring.canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single layer of entities. A layer is simply a list of
 * EntityViews.
 * 
 * @author jimmy
 *
 */
public class Layer
{
	private List<EntityView> entities;
	private String name;

	/*
	 * Make a new Layer, which represents a list of EntityViews.
	 */
	public Layer()
	{
		entities = new ArrayList<EntityView>();
		name = null;
	}
	
	public Layer(String s)
	{
		entities = new ArrayList<EntityView>();
		name = s;
	}
	
	
	/**
	 * Set layer name
	 */
	public void setLayerName(String s){
		this.name = s;
	}
	
	/**
	 * Get layer name
	 * @return String name
	 */
	public String getLayerName(){
		return this.name;
	}

	/**
	 * Get the list of EntityViews that is represented by this Layer.
	 * 
	 * @return list of EntityViews.
	 */
	public List<EntityView> getEntities()
	{
		return entities;
	}

	/**
	 * Set the list of EntityViews that is represented by this Layer.
	 * 
	 * @param entities
	 *            List of EntityViews that this Layer will now describe
	 */
	public void setEntities(List<EntityView> entities)
	{
		this.entities = entities;
	}

	/**
	 * Add the given EntityView to the layer.
	 * 
	 * @param entity
	 */
	public void addEntity(EntityView entity)
	{
		this.entities.add(entity);
	}

	public List<EntityView> getSelectedEntities()
	{
		List<EntityView> selectedEntities = new ArrayList<EntityView>();
		for (EntityView entity : entities) {
			if (entity.isSelected()) {
				selectedEntities.add(entity);
			}
		}
		return selectedEntities;
	}
}