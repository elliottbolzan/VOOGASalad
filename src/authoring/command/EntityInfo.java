package authoring.command;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import engine.entities.Entity;
import game_data.EntityConverter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class EntityInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5310590949892001516L;
	private transient Entity entity;
	private String extension;
	// private transient Image image;
	private String xmlString;

	public EntityInfo(Entity entity)
	{
		this.entity = entity;
		xmlString = "";
		extension = entity.getImagePath().replaceAll("^.*\\.(.*)$", "$1");
	}

	public Entity getEntity()
	{
		return entity;
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new EntityConverter());
		xmlString = (String) s.readObject();
		entity = (Entity) xStream.fromXML(xmlString);
		extension = entity.getImagePath().replaceAll("^.*\\.(.*)$", "$1");
		String tempDir = System.getProperty("java.io.tmpdir");
		File dir = new File(tempDir);
		File filename = File.createTempFile(entity.getName() + "Image", "." + extension, dir);
		RenderedImage renderedImage = SwingFXUtils.fromFXImage(SwingFXUtils.toFXImage(ImageIO.read(s), null), null);
		ImageIO.write(renderedImage, extension, filename);
		entity.setImagePath("file:" + filename.getAbsolutePath());
		System.out.println(entity.getImagePath());
	}

	private void writeObject(ObjectOutputStream s) throws IOException
	{
		s.defaultWriteObject();
		extension = entity.getImagePath().replaceAll("^.*\\.(.*)$", "$1");
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new EntityConverter());
		xmlString = xStream.toXML(entity);
		s.writeObject(xmlString);

		System.out.println(extension);
		ImageIO.write(SwingFXUtils.fromFXImage(new Image(entity.getImagePath()), null), extension, s);
	}
}