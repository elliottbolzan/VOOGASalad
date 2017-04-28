package authoring.command;

import java.util.Random;

public class AddInfo extends SingleEntityCommandInfo
{

	private static final long serialVersionUID = 1199205996330909954L;
	private double xPos;
	private double yPos;
	private double width;
	private double height;
	// private ImageOutputStream imageStream;
	// private transient Image image;

	public AddInfo(String entityName, double xPos, double yPos)
	{
		super(entityName, new Random().nextLong());
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public double getX()
	{
		return xPos;
	}

	public double getY()
	{
		return yPos;
	}
	//
	// private void readObject(ObjectInputStream s) throws IOException,
	// ClassNotFoundException
	// {
	// s.defaultReadObject();
	// image = SwingFXUtils.toFXImage(ImageIO.read(s), null);
	// }
	//
	// private void writeObject(ObjectOutputStream s) throws IOException
	// {
	// s.defaultWriteObject();
	// ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", s);
	// }

}
