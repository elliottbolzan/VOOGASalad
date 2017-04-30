package authoring.command;

import authoring.networking.Packet;

/**
 * 
 * @author jimmy
 *
 */
public abstract class EntityCommandInfo extends Packet
{
	private static final long serialVersionUID = -8684242471160075071L;

	private String entityName;

	public EntityCommandInfo(String entityName)
	{
		this.entityName = entityName;
	}

	public String getEntityName()
	{
		return entityName;
	}
}
