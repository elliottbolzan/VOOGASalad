package authoring.canvas;

import java.util.ArrayList;
import java.util.List;

public class Layer
{
	private List<EntityDisplay> entities;

	public Layer()
	{
		entities = new ArrayList<EntityDisplay>();
	}

	public List<EntityDisplay> getEntities()
	{
		return entities;
	}

	public void setEntities(List<EntityDisplay> entities)
	{
		this.entities = entities;
	}

	public void addEntity(EntityDisplay entity)
	{
		this.entities.add(entity);
	}
}
