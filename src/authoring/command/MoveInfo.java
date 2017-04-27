package authoring.command;

public class MoveInfo extends EntityCommandInfo
{

	private static final long serialVersionUID = 1540267030725337292L;
	private double shiftX;
	private double shiftY;

	public MoveInfo(long entityId, double shiftX, double shiftY)
	{
		super(entityId);
		this.shiftX = shiftX;
		this.shiftY = shiftY;
	}

	public double getShiftX()
	{
		return shiftX;
	}

	public double getShiftY()
	{
		return shiftY;
	}
}
