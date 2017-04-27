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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameLoader {

	private ResourceManager resourceManager;

	/**
	 * Loads game given the folder path and returns the entities and songpath
	 * necessary
	 * 
	 * @param gameFolderPath
	 *            : path to load game from
	 * @return
	 * @throws NotAGameFolderException
	 *             : incorrect folder path exception
	 */
	public Game loadGame(String gameFolderPath, String saveName) throws Exception {
		
		String tempFolderPath = System.getProperty("java.io.tmpdir");
		(new Unpackager()).unzip(gameFolderPath, System.getProperty("java.io.tmpdir"));
		
		//(new Unpackager()).unzip(gameFolderPath, gameFolderPath.replace(".vs", ""));
		//gameFolderPath = gameFolderPath.replace(".vs", "");
		//File dataFile = new File(gameFolderPath + File.separator + saveName);
		
		File dataFile = new File(tempFolderPath + File.separator + saveName);
		if (!dataFile.exists()) {
			throw new NotAGameFolderException();
		}
		
		resourceManager = new ResourceManager();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.parse(tempFolderPath + File.separator + saveName);
		//Document doc = docBuilder.parse(gameFolderPath + File.separator + saveName);
		
		Game game = new Game();
		addName(game, doc);
		addInfo(game, doc);
		addLevels(game, doc, tempFolderPath);
		addDefaults(game, doc, tempFolderPath);
		addSong(game, doc, tempFolderPath);
		addSaves(game, tempFolderPath);
		
		//addLevels(game, doc, gameFolderPath);
		//addDefaults(game, doc, gameFolderPath);
		//addSong(game, doc, gameFolderPath);
		
		return game;
	}

	private void addAchievements(Game game, Document doc, String folderPath) {
		NodeList achieveNode = doc.getElementsByTagName("Achievements");
		//game.setAchievements(achieveNode.item(0).getAttributes().item(0).getNodeValue());
	}
	
	private void addSaves(Game game, String folderPath){
		ObservableList<String> saves = FXCollections.observableArrayList();
		File folder = new File(folderPath);
		File[] allFiles = folder.listFiles();
		for(File file : allFiles){
			if(file.getName().contains("save") && file.getName().contains(".xml")){
				saves.add(file.getName());
			}
		}
		game.setSaves(saves);
	}

	private void addInfo(Game game, Document doc) {
		NodeList infoNode = doc.getElementsByTagName("GameInfo");
		game.setInfo(infoNode.item(0).getAttributes().item(0).getNodeValue());
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
		NodeList nameNodes = doc.getElementsByTagName(resourceManager.getNameTitle());
		game.setName(nameNodes.item(0).getAttributes().item(0).getNodeValue());
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
			NodeList songNodes = doc.getElementsByTagName(resourceManager.getResourceTitle());
			game.setSongPath(gameFolderPath + File.separator
					+ convertPathForSystem(songNodes.item(0).getAttributes().item(0).getNodeValue()));
		} catch (Exception e) {
			game.setSongPath("");
		}
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
		NodeList defaultsNode = doc.getElementsByTagName(resourceManager.getDefaultsTitle());
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
		NodeList levelsNode = doc.getElementsByTagName(resourceManager.getLevelsTitle());
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
		
		Element cameraNode = (Element) levelElement.getChildNodes().item(1);
		Entity camera = getEntityFromElement(cameraNode, gameFolderPath);
		returnedLevel.setCamera((CameraEntity) camera);
		
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
			if (entitiesList.item(i).getNodeName().equals(resourceManager.getEntityState())) {
				Entity instantiatedEntity = getEntityFromElement((Element) entitiesList.item(i), gameFolderPath);
				entityList.add(instantiatedEntity);
			}
		}
		return entityList;
	}

	/**
	 * Converts an element node from XML into an entity
	 * 
	 * @param entityElement
	 *            : element to be converted into an entity
	 * @return
	 */
	private Entity getEntityFromElement(Element entityElement, String gameFolderPath) {
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new EntityConverter());
		
		Entity entity = (Entity) xStream.fromXML(getXMLStringFromElement(entityElement));
		entity.setImagePath("file:" + gameFolderPath + File.separator + convertPathForSystem(entity.getImagePath()));
		
		return entity;
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