package authoring.command;

public abstract class EntityCommandInfo extends CommandInfo
{
	private static final long serialVersionUID = 7742404661749847649L;
	private long entityId;

	public EntityCommandInfo(long entityId)
	{
		this.entityId = entityId;
	}

	public long getEntityId()
	{
		return entityId;
	}

}
