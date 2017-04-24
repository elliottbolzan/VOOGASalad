package game_data;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import engine.Entity;
import engine.entities.CameraEntity;
import engine.game.Level;
import exceptions.NotAGameFolderException;

public class GameLoader {

	private Unpackager unzip;
	private ResourceManager rm;

	/**
	 * Loads game given the folder path and returns the entities and songpath
	 * necessary
	 * 
	 * @param gameFolderPath
	 *            : folderpath to load game from
	 * @return
	 * @throws NotAGameFolderException
	 *             : incorrect folder path exception
	 */
	public Game loadGame(String gameFolderPath, String saveName) throws Exception {
		unzip = new Unpackager();
		String newPath = "";

		if (gameFolderPath.contains(".zip")) {
			newPath = gameFolderPath.replace(".zip", "");
			unzip.unzip(gameFolderPath, newPath);
		}

		File dataFile = new File(newPath + File.separator + saveName);
		rm = new ResourceManager();
		
		if (!dataFile.exists()) {
			throw new NotAGameFolderException();
		}
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		doc = docBuilder.parse(newPath + File.separator + saveName);
		Game game = new Game();

		addName(game, doc);
		addLevels(game, doc, newPath);
		addDefaults(game, doc, newPath);
		addSong(game, doc, newPath);
		// addAchieve(game, doc, newPath);
		// addBackground(game, doc, newPath);
		addInfo(game, doc, newPath);
		addCamera(game, doc, newPath);
		game.setCurrentPath(gameFolderPath);
		return game;
	}

	private void addAchieve(Game game, Document doc, String folderPath) {
		NodeList achieveNode = doc.getElementsByTagName("Achievements");
		game.setName(achieveNode.item(0).getAttributes().item(0).getNodeValue());
	}

	private void addBackground(Game game, Document doc, String folderPath) {
		try {
			NodeList nodes = doc.getElementsByTagName("Resources");
			game.setBackPath(folderPath + File.separator + nodes.item(0).getAttributes().item(0).getNodeValue());
		} catch (Exception e) {
			game.setBackPath("");
		}
	}

	private void addInfo(Game game, Document doc, String folderPath) {
		NodeList infoNode = doc.getElementsByTagName("GameInfo");
		//game.setName(infoNode.item(0).getAttributes().item(0).getNodeValue());
	}

	/**
	 * Adds game name from document to game
	 * 
	 * @param game
	 *            : game where name is to be added
	 * @param doc
	 *            : Document that contains game name, extracted by XML
	 */
	private void addName(Game game, Document doc) {
		NodeList nameNodes = doc.getElementsByTagName(rm.getNameTitle());
		//game.setName(nameNodes.item(0).getAttributes().item(0).getNodeValue());
	}

	/**
	 * Adds game song from document to game
	 * 
	 * @param game
	 *            : game where song is to be added
	 * @param doc
	 *            : Document that contains song, extracted by XML
	 * @param gameFolderPath
	 *            : top-level directory of the game
	 */
	private void addSong(Game game, Document doc, String gameFolderPath) {
		try {
			NodeList songNodes = doc.getElementsByTagName(rm.getResourceTitle());
			game.setSongPath(gameFolderPath + File.separator
					+ convertPathForSystem(songNodes.item(0).getAttributes().item(0).getNodeValue()));
		} catch (Exception e) {
			game.setSongPath("");
		}
	}

	/**
	 * Adds game camera from document to game
	 * 
	 * @param game
	 *            : game where song is to be added
	 * @param doc
	 *            : Document that contains song, extracted by XML
	 * @param gameFolderPath
	 *            : top-level directory of the game
	 */
	private void addCamera(Game game, Document doc, String gameFolderPath) {
		// TODO
		Element cameraNode = (Element) doc.getElementsByTagName(rm.getCameraTitle()).item(0);
		Entity camera = getEntityFromElement(cameraNode);
		camera.setImagePath("file:" + gameFolderPath + File.separator + convertPathForSystem(camera.getImagePath()));
		game.setCamera((CameraEntity) camera);
	}

	/**
	 * Method to add default entities to game after they are extracted from
	 * Document
	 * 
	 * @param game
	 *            : game object containing entities needed to be added
	 * @param doc
	 *            : Document containing information from extracted folder
	 * @param gameFolderPath
	 *            : top-level directory of the game
	 */
	private void addDefaults(Game game, Document doc, String gameFolderPath) {
		NodeList defaultsNode = doc.getElementsByTagName(rm.getDefaultsTitle());
		Element entitiesNode = (Element) defaultsNode.item(0).getChildNodes().item(0);
		game.setDefaults(getEntities(entitiesNode, gameFolderPath));
	}

	/**
	 * Method to add levels to the game, as they are extracted from XML
	 * 
	 * @param game
	 *            : game where levels are to be added
	 * @param doc
	 *            : Document that contains level information
	 * @param gameFolderPath
	 *            : top-level directory of the game
	 */
	private void addLevels(Game game, Document doc, String gameFolderPath) {
		NodeList levelsNode = doc.getElementsByTagName(rm.getLevelsTitle());
		NodeList levelsList = levelsNode.item(0).getChildNodes();
		List<Level> gameLevels = new ArrayList<Level>();

		for (int i = 0; i < levelsList.getLength(); i++) {
			Element levelElement = (Element) levelsList.item(i);
			Level instantiatedLevel = convertElementToLevel(levelElement, gameFolderPath);
			gameLevels.add(instantiatedLevel);
		}
		game.setLevels(gameLevels);
	}

	/**
	 * Converts an element from the XML into a level by filling it with entities
	 * 
	 * @param levelElement
	 *            : extracted level element from XML
	 * @param gameFolderPath
	 *            : top-level directory of the game
	 * @return
	 */
	private Level convertElementToLevel(Element levelElement, String gameFolderPath) {
		Element entitiesNode = (Element) levelElement.getChildNodes().item(0);
		Level returnedLevel = new Level();
		for (Entity entity : getEntities(entitiesNode, gameFolderPath)) {
			returnedLevel.addEntity(entity);
		}
		return returnedLevel;
	}

	/**
	 * Method to extract entities from nodes and return so game can be populated
	 * 
	 * @param entitiesNode
	 *            : node of entities that will be extracted
	 * @param folderPath
	 *            : top-level directory of the game
	 * @return
	 */
	private List<Entity> getEntities(Element entitiesNode, String gameFolderPath) {
		NodeList entitiesList = entitiesNode.getChildNodes();
		List<Entity> entityList = new ArrayList<Entity>();
		for (int i = 0; i < entitiesList.getLength(); i++) {
			if (entitiesList.item(i).getNodeName().equals(rm.getEntityState())) {
				Entity instantiatedEntity = getEntityFromElement((Element) entitiesList.item(i));
				instantiatedEntity.setImagePath("file:" + gameFolderPath + File.separator
						+ convertPathForSystem(instantiatedEntity.getImagePath()));
				entityList.add(instantiatedEntity);
			}
		}
		return entityList;
	}

	/**
	 * Converts file separators to match the system
	 * 
	 * @param path
	 * @return
	 */
	private String convertPathForSystem(String path) {
		String newPath = path;
		if (File.separator.equals("/")) {
			if (path.contains("\\")) {
				newPath = path.replace("\\", File.separator);
			}
		} else {
			if (path.contains("/")) {
				newPath = path.replace("/", File.separator);
			}
		}

		return newPath;
	}

	/**
	 * Converts an element node from XML into an entity
	 * 
	 * @param entityElement
	 *            : element to be converted into an entity
	 * @return
	 */
	private Entity getEntityFromElement(Element entityElement) {
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new EntityConverter());
		Entity entity = (Entity) xStream.fromXML(getXMLStringFromElement(entityElement));

		return entity;
	}

	// http://stackoverflow.com/questions/32739278/convert-elementorg-w3c-dom-to-string-in-java
	/**
	 * Method to convert an element node from XML into a string
	 * 
	 * @param entityElement
	 *            element to be converted into a string
	 * @return
	 */
	private String getXMLStringFromElement(Element entityElement) {
		StreamResult result = null;
		NodeList entityChildren = entityElement.getChildNodes();
		Element entityXMLElement = null;
		for (int i = 0; i < entityChildren.getLength(); i++) {
			if (entityChildren.item(i).getNodeType() == Node.ELEMENT_NODE) {
				entityXMLElement = (Element) entityChildren.item(i);
			}
		}
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(entityXMLElement);
			result = new StreamResult(new StringWriter());
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO
		}
		String strObject = result.getWriter().toString();
		return strObject;
	}
}