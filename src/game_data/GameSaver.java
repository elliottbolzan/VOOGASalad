package game_data;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import engine.Entity;
import engine.game.Level;

// Make references to paths relative. 
public class GameSaver {

	private Game game;
	private GameXMLFactory gameXMLFactory;

	public void saveGame(Game game, String filepath) {
		this.game = game;
		gameXMLFactory = new GameXMLFactory();
		gameXMLFactory.setName(game.getName());
		createRoot(filepath);
		saveLevels(game.getLevels(), filepath + File.separator + game.getName());
		saveDefaults(game.getDefaults(), filepath + File.separator + game.getName());
		saveSong(filepath + File.separator + game.getName(), game.getSongPath());
		saveDocument(filepath);
	}

	private void createRoot(String filePath) {
		File folder = new File(filePath + File.separator + game.getName());
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

	private void saveSong(String filePath, String songPath) {
		try {
			String updated = new File(songPath).getAbsolutePath();
			Path sourcePath = Paths.get(updated);
			String relative = "resources" + File.separator + game.getName() + ".mp3";
			game.setSongPath(relative);
			File file = new File(filePath + File.separator + relative);
			file.getParentFile().mkdirs();
			file.createNewFile();
			Path targetPath = Paths.get(filePath + File.separator + relative);
			Files.copy(sourcePath, targetPath, REPLACE_EXISTING);
			gameXMLFactory.addSong(relative);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveEntityImage(Entity entity, String filePath) {
		try {
			String sourcePathString = new File(new URI(entity.getImagePath())).getAbsolutePath();
			Path sourcePath = Paths.get(sourcePathString);

			String targetPathString = "resources" + File.separator + entity.getName() + "Image.png";
			entity.setImagePath(targetPathString);
			File entityImageFile = new File(filePath + File.separator + targetPathString);

			entityImageFile.getParentFile().mkdirs();
			entityImageFile.createNewFile();

			Path targetPath = Paths.get(filePath + File.separator + targetPathString);
			Files.copy(sourcePath, targetPath, REPLACE_EXISTING);

		} catch (Exception i) {
			// i.printStackTrace();
		}
	}

	private void saveDefaults(List<Entity> defaults, String filePath) {

		List<Entity> entities = defaults;
		List<Element> entityNodes = new ArrayList<Element>();

		for (int j = 0; j < entities.size(); j++) {
			Entity currEntity = entities.get(j);

			Element entityNode = getEntityNode(currEntity, filePath);
			entityNodes.add(entityNode);
		}
		LevelSaver ls = new LevelSaver(entityNodes);
		String xmlLevel = ls.saveLevel();
		// System.out.println(xmlLevel);
		Element levelElement = gameXMLFactory.stringToElement(xmlLevel);
		gameXMLFactory.addDefaultEntity(levelElement);
		// System.out.println(xmlLevel);

	}

	private void saveLevels(List<Level> levels, String filePath) {
		for (int i = 0; i < levels.size(); i++) {
			List<Entity> entities = new ArrayList<Entity>(levels.get(i).getEntities());
			List<Element> entityNodes = new ArrayList<Element>();

			for (int j = 0; j < entities.size(); j++) {
				Entity currEntity = entities.get(j);
				Element entityNode = getEntityNode(currEntity, filePath);
				entityNodes.add(entityNode);
			}

			LevelSaver ls = new LevelSaver(entityNodes);
			String xmlLevel = ls.saveLevel();
			// System.out.println(xmlLevel);
			Element levelElement = gameXMLFactory.stringToElement(xmlLevel);
			gameXMLFactory.addLevel(levelElement);
			// System.out.println(xmlLevel);
		}
	}

	private Element getEntityNode(Entity entity, String folderPath) {
		String tempImagePath = entity.getImagePath();
		saveEntityImage(entity, folderPath);
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new EntityConverter());
		String xmlString = xStream.toXML(entity);
		entity.setImagePath(tempImagePath);
		return gameXMLFactory.stringToElement(xmlString);
	}

	private void saveDocument(String filePath) {
		Document doc = gameXMLFactory.getDocument();

		File levelDirectory = new File(filePath);
		if (!levelDirectory.exists()) {
			levelDirectory.mkdirs();
		}
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(
					new File(filePath + File.separator + game.getName() + File.separator + "settings.xml"));
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}