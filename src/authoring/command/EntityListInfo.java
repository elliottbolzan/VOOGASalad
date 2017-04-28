package authoring.command;

import java.util.ArrayList;
import java.util.List;

import authoring.networking.Packet;
import engine.Entity;

public class EntityListInfo extends Packet
{

	private static final long serialVersionUID = 1199205996330909954L;
	private List<EntityInfo> entityInfo;

	public EntityListInfo(List<? extends Entity> addedSublist)
	{
		entityInfo = new ArrayList<EntityInfo>();
		addedSublist.forEach(e -> {
			entityInfo.add(new EntityInfo(e));
		});
	}

	public List<Entity> getEntities()
	{
		List<Entity> returned = new ArrayList<Entity>();
		entityInfo.forEach(e -> {
			returned.add(e.getEntity());
		});
		return returned;
	}
}
