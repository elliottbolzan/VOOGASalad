package authoring.command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class AddInfo extends EntityCommandInfo
{

	private static final long serialVersionUID = 1199205996330909954L;
	private double xPos;
	private double yPos;
	private double width;
	private double height;
	// private ImageOutputStream imageStream;
	private transient Image image;

	public AddInfo(long entityId, double xPos, double yPos, double width, double height, Image image)
	{
		super(entityId);
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.image = image;
	}

	public double getX()
	{
		return xPos;
	}

	public double getY()
	{
		return yPos;
	}

	public double getWidth()
	{
		return width;
	}

	public double getHeight()
	{
		return height;
	}

	public Image getImage()
	{
		return image;
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();
		image = SwingFXUtils.toFXImage(ImageIO.read(s), null);
	}

	private void writeObject(ObjectOutputStream s) throws IOException
	{
		s.defaultWriteObject();
		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", s);
	}

}
