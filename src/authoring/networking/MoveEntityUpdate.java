package authoring.networking;

public class MoveEntityUpdate extends EntityUpdate
{
	double newX;
	double newY;

	public MoveEntityUpdate(double newX, double newY, long entityId)
	{
		super(entityId);
	}

	public double getX()
	{
		return newX;
	}

	public double getY()
	{
		return newY;
	}
}
