package authoring.command;

public class ResizeInfo extends EntityCommandInfo
{

	private static final long serialVersionUID = 1199205996330909954L;
	private double deltaWidth;
	private double deltaHeight;

	public ResizeInfo(long entityId, double deltaWidth, double deltaHeight)
	{
		super(entityId);
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
