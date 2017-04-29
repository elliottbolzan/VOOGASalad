package game_data;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Element;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import engine.entities.Entity;

/**
 * @author Jay Doherty
 *
 */
public class EntitySaver {

	private GameXMLFactory gameXMLFactory;
	
	public EntitySaver(GameXMLFactory gameXMLFactory) {
		this.gameXMLFactory = gameXMLFactory;
	}

	/**
	 * Generates a XML Element given a list of entities. Also saves the images of all entities
	 * in the process.
	 * @param entities : entities to save
	 * @param gameFolderPath : path of the top-level game folder
	 * @return XML element for the list of entities
	 */
	protected List<Element> getEntityListAsXML(Collection<Entity> entities, String gameFolderPath) {
		List<Element> entityNodes = new ArrayList<Element>();

		for(Entity entity : entities) {
			Element xmlEntity = this.getEntityAsXML(entity, gameFolderPath);
			entityNodes.add(xmlEntity);
		}

		return entityNodes;
	}
	
	/**
	 * Converts an entity into an element node to be used in XML.
	 * Also saves the entity's image into game resources.
	 * @param entity : Entity to be converted into element
	 * @param gameFolderPath : top-level directory of the game
	 * @return XML Element for given Entity
	 */
	protected Element getEntityAsXML(Entity entity, String gameFolderPath) {
		String absoluteImagePath = entity.getImagePath();
		String relativeImagePath = "resources" + File.separator + entity.getName() + "Image.png";
		this.saveImage(absoluteImagePath, relativeImagePath, gameFolderPath);

		entity.setImagePath(relativeImagePath);
		
		
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new EntityConverter());
		String xmlString = xStream.toXML(entity);
		
		entity.setImagePath(absoluteImagePath);
		/*System.out.println("======================");
		System.out.println(entity);
		System.out.println(entity.getEvents());
		System.out.println("XMLSTRING");(/
	*/
		try (PrintStream out = new PrintStream(new FileOutputStream("filename" + entity.getName() + ".txt"))) {
		    out.print(xmlString);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 	/*System.out.println("XMLSTRING: " + xmlString);*/
		return gameXMLFactory.stringToElement(xmlString);
	}
	
	/**
	 * Saves the image path of the entities into XML file
	 * @param entity : entity's image path to be saved
	 * @param gameFolderPath : top-level directory of the game
	 */
	private void saveImage(String originalImagePath, String relativeImagePath, String gameFolderPath) {
		try {
			File originalImageFile = new File(new URI(originalImagePath));
			String savedImagePath = gameFolderPath + File.separator + relativeImagePath;
			this.makeFile(savedImagePath);
			this.copyFileContents(originalImageFile, savedImagePath);
		} catch (IOException | URISyntaxException e) {
			//TODO
		}
	}
	
	/**
	 * Makes a a new empty file for the given path.
	 */
	private void makeFile(String filePath) throws IOException {
		File file = new File(filePath);
		file.getParentFile().mkdirs();
		file.createNewFile();
	}

	/**
	 * Copies the contents of one file to a destination file path.
	 */
	private void copyFileContents(File originalFile, String destinationPath) throws IOException {
		Path sourcePath = Paths.get(originalFile.getAbsolutePath());
		Path targetPath = Paths.get(destinationPath);
		Files.copy(sourcePath, targetPath, REPLACE_EXISTING);
	}
}
