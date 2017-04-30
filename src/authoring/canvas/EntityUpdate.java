package authoring.canvas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import authoring.networking.Packet;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * 
 * @author jimmy
 *
 */
public class EntityUpdate extends Packet {
	private static final long serialVersionUID = 1930810473204140633L;
	private String EXTENSION = "gif";

	private transient Image image;
	private ImageOutputStream imageStream;
	private double x;
	private double y;
	private double width;
	private double height;
	private String name;

	/**
	 * Creates n EntityUpdate
	 * 
	 * @param username
	 *            the username of the Message's sender.
	 * @param message
	 *            the content of the Message.
	 */
	public EntityUpdate(Image image, double x, double y, double width, double height, String name) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
	}

	/**
	 * @return the Message's username.
	 */
	public Image getImage() {
		return image;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	/**
	 *
	 * @return the Entity's width after update
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @return the Entity's height after update
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @return the Entity's name.
	 */
	public String getName() {
		return name;
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		image = SwingFXUtils.toFXImage(ImageIO.read(s), null);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", s);
	}
}
