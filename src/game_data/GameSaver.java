package game_data;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.transform.OutputKeys;
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
import engine.entities.CameraEntity;
import engine.game.Level;
public class GameSaver
{
	private static final String SETTINGS_FILE_NAME = "settings.xml";
	private Game game;
	private GameXMLFactory gameXMLFactory;
	private Packager zipper;

	/**
	 * Main method to save the entire game to the selected file path. Utilizes GameXMLFactory to create the XML file.
	 * @param game : game to be saved
	 * @param parentDirectoryPath : path to the parent directory in which the game folder will be created and saved
	 */
	public void saveGame(Game game, String parentDirectoryPath) {
		this.game = game;

		gameXMLFactory = new GameXMLFactory();

		gameXMLFactory.setName(game.getName());
		zipper = new Packager();


		String gameFolderPath = parentDirectoryPath + File.separator + game.getName();
		createFolder(gameFolderPath);
		saveLevels(game.getLevels(), gameFolderPath);
		saveDefaults(game.getDefaults(), gameFolderPath);
		saveSong(game.getSongPath(), gameFolderPath);
		//saveBackground(gameFolderPath, game.getSongPath());
		saveCamera(game.getCamera(), gameFolderPath);
		//saveAchievements("achievements", gameFolderPath);
		saveGameInfo(gameFolderPath, "information about game");
		saveDocument(gameFolderPath, "settings.xml");
		try {
			zipDoc(parentDirectoryPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void zipDoc(String path) throws IOException{
		List<File> toCompress = new ArrayList<File>();
		File dir = new File(path + File.separator + game.getName());
		File[] allFiles = dir.listFiles();
		if (allFiles != null) {
			for (File child : allFiles) {
				toCompress.add(child);
			}
		}
		zipper.packZip(new File(path + File.separator + game.getName() + ".zip"), toCompress);
		deleteDir(dir);
	}

	private boolean deleteDir(File dir){
		dir.listFiles();
		File[] files = dir.listFiles();	    		
		if(null!=files){
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDir(files[i]);
				}
				else {
					files[i].delete();
				}
			}
		}
		return(dir.delete());
	}

	/**
	 * Saves the state of the game to a non hard coded file in the folder
	 * @param game
	 * @param parentDirectoryPath
	 * @param saveName
	 */
	public void saveGameState(Game game, String gameFolderPath, String saveName){
		this.game = game;

		gameXMLFactory = new GameXMLFactory();
		gameXMLFactory.setName(game.getName());

		saveDefaults(game.getDefaults(), gameFolderPath);
		saveSong(game.getSongPath(), gameFolderPath);
		saveCamera(game.getCamera(), gameFolderPath);
		saveLevels(game.getLevels(), gameFolderPath);
		saveDocument(gameFolderPath, saveName);

	}

	/**
	 * Saves the document as a whole, after the XML serializing is done
	 * @param gameFolderPath : top-level directory of the game
	 */
	private void saveDocument(String gameFolderPath, String filename) {
		Document doc = gameXMLFactory.getDocument();
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(gameFolderPath + File.separator + filename));
			transformer.transform(source, result);
		} catch (TransformerException e) {
			//TODO
		}
	}
	/**
	 * Saves the default entities into XML.
	 * @param defaults : List of entities that are defaults, to be saved into XML
	 * @param gameFolderPath : top-level directory of the game
	 */
	private void saveDefaults(List<Entity> defaults, String gameFolderPath) {
		Element levelElement = this.getEntityListAsXML(defaults, gameFolderPath);
		gameXMLFactory.addDefaultEntity(levelElement);
	}
	/**
	 * Saves the list of levels (list of entities) that will be written into XML.
	 * @param levels : list of levels to be written to XML
	 * @param gameFolderPath : top-level directory of the game
	 */
	private void saveLevels(List<Level> levels, String gameFolderPath) {
		for (Level level : levels) {
			Element levelElement = this.getEntityListAsXML(level.getEntities(), gameFolderPath);
			gameXMLFactory.addLevel(levelElement);
		}
	}
	/**
	 * Saves the song path into the XML game file
	 * @param gameFolderPath : top-level directory of the game
	 * @param originalSongPath : song path to be saved into XML
	 */
	private void saveSong(String originalSongPath, String gameFolderPath) {
		if (originalSongPath.equals("")) {
			return;
		}
		try {
			String relativePath = "resources" + File.separator + game.getName() + ".mp3";
			game.setSongPath(relativePath);
			gameXMLFactory.addSong(relativePath);

			File originalSongFile = new File(originalSongPath);
			String savedSongPath = gameFolderPath + File.separator + relativePath;
			this.makeFile(savedSongPath);
			this.copyFileContents(originalSongFile, savedSongPath);
		} catch (Exception e) {
			//TODO
		}
	}	

	/**
	 * Saves achievements into XML file
	 * @param achieve
	 * @param filePath
	 */
	private void saveAchievements(String achieve, String filePath){
		if(achieve.equals("")) return;
		gameXMLFactory.addAchievement(achieve);
	}

	/**
	 * @param camera
	 * @param gameFolderPath
	 */
	private void saveCamera(CameraEntity camera, String gameFolderPath) {
		Element cameraElement = this.getEntityAsXML(camera, gameFolderPath);
		gameXMLFactory.addCamera(cameraElement);
	}

	/**
	 * Saves the image path of the entities into XML file
	 * @param entity : entity's image path to be saved
	 * @param gameFolderPath : top-level directory of the game
	 */
	private void saveEntityImage(String originalImagePath, String relativeImagePath, String gameFolderPath) {
		try {
			File originalImageFile = new File(new URI(originalImagePath));
			String savedImagePath = gameFolderPath + File.separator + relativeImagePath;
			this.makeFile(savedImagePath);
			this.copyFileContents(originalImageFile, savedImagePath);
		} catch (Exception e) {
			//TODO
		}
	}
	/**
	 * 
	 * @param filePath
	 * @param backPath
	 */
	public void saveBackground(String backPath, String filePath){
		try {
			String sourcePathString = new File(new URI(backPath)).getAbsolutePath();
			Path sourcePath = Paths.get(sourcePathString);
			String targetPathString = "resources" + File.separator + "Background.png";
			File backImageFile = new File(filePath + File.separator + targetPathString);

			backImageFile.getParentFile().mkdirs();
			backImageFile.createNewFile();

			Path targetPath = Paths.get(filePath + File.separator + targetPathString);
			Files.copy(sourcePath, targetPath, REPLACE_EXISTING);
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @param filePath
	 * @param info
	 */
	public void saveGameInfo(String filePath, String info){
		if(info.equals("")) return;
		gameXMLFactory.addInfo(info);
	}

	/**
	 * Generates a XML Element given a list of entities. Also saves the images of all entities
	 * in the process.
	 * @param entities : entities to save
	 * @param gameFolderPath : path of the top-level game folder
	 * @return XML element for the list of entities
	 */
	private Element getEntityListAsXML(Collection<Entity> entities, String gameFolderPath) {
		List<Element> entityNodes = new ArrayList<Element>();

		for(Entity entity : entities) {
			Element entityNode = getEntityAsXML(entity, gameFolderPath);
			entityNodes.add(entityNode);
		}

		LevelSaver ls = new LevelSaver(entityNodes);
		String xmlLevel = ls.saveLevel();
		return gameXMLFactory.stringToElement(xmlLevel);
	}

	/**
	 * Converts an entity into an element node to be used in XML.
	 * Also saves the entity's image into game resources.
	 * @param entity : Entity to be converted into element
	 * @param gameFolderPath : top-level directory of the game
	 * @return XML Element for given Entity
	 */
	private Element getEntityAsXML(Entity entity, String gameFolderPath) {
		String absoluteImagePath = entity.getImagePath();
		String relativeImagePath = "resources" + File.separator + entity.getName() + "Image.png";
		saveEntityImage(absoluteImagePath, relativeImagePath, gameFolderPath);

		entity.setImagePath(relativeImagePath);
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new EntityConverter());
		String xmlString = xStream.toXML(entity);
		
		entity.setImagePath(absoluteImagePath);
		return gameXMLFactory.stringToElement(xmlString);
	}

	/**
	 * Creates the folder for the game
	 */
	private void createFolder(String gameFolderPath) {
		File folder = new File(gameFolderPath);
		if (!folder.exists()) {
			folder.mkdirs();
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