package authoring.command;

public abstract class SingleEntityCommandInfo extends EntityCommandInfo
{
	private static final long serialVersionUID = 7742404661749847649L;
	private long entityId;

	public SingleEntityCommandInfo(String entityName, long entityId)
	{
		super(entityName);
		this.entityId = entityId;
	}

	public long getEntityId()
	{
		return entityId;
	}

}
