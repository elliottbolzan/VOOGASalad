package authoring.command;

public class ResizeInfo extends SingleEntityCommandInfo
{

	private static final long serialVersionUID = 1199205996330909954L;
	private double deltaWidth;
	private double deltaHeight;

	public ResizeInfo(String entityName, long entityId, double deltaWidth, double deltaHeight)
	{
		super(entityName, entityId);
		this.deltaWidth = deltaWidth;
		this.deltaHeight = deltaHeight;
	}

	public double getDeltaHeight()
	{
		return deltaHeight;
	}

	public double getDeltaWidth()
	{
		return deltaWidth;
	}

}
