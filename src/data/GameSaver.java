package data;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import engine.entities.Entity;
import engine.game.Level;

/**
 * This class provides the main functionality for saving games from the
 * authoring environment or from the player. It compresses the game files into a
 * zip file for easy distribution.
 * 
 * @author Michael Li
 * @author Jay Doherty
 *
 */
public class GameSaver {
	private GameXMLFactory gameXMLFactory;
	private ResourceManager resourceManager = new ResourceManager();

	/**
	 * Main method to save the entire game to the selected file path. Utilizes
	 * GameXMLFactory to create the XML file.
	 * 
	 * @param game
	 *            : game to be saved
	 * @param parentDirectoryPath
	 *            : path to the parent directory in which the game folder will
	 *            be created and saved
	 */
	protected void saveGame(Game game, String parentDirectoryPath) {
		String gameFolderPath = parentDirectoryPath + File.separator + game.getName();
		createFolder(gameFolderPath);

		this.saveAndCompress(game, gameFolderPath, resourceManager.getFileName() + resourceManager.getXML());

	}

	/**
	 * Alternate method to save the entire game that is currently being played
	 * to the selected file path. Utilizes GameXMLFactory to create the XML
	 * file.
	 * 
	 * @param game
	 *            : game to be saved
	 * @param zipFolderPath
	 *            : path to zip Folder that represents the current game being
	 *            played
	 * @param saveName
	 *            : name of the new file being added to the game
	 */
	protected void saveGameState(Game game, String zipFolderPath, String saveName) {
		String gameFolderPath = zipFolderPath.replace(resourceManager.getVS(), "");
		try {
			(new Unpackager()).unzip(zipFolderPath, gameFolderPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.saveAndCompress(game, gameFolderPath, saveName);
	}

	/**
	 * saves the game as a folder, and compresses the folder created into a
	 * single .vs file
	 * 
	 * @param game
	 *            : game to be saved
	 * @param gameFolderPath
	 *            : path to game Folder that represents the current game being
	 *            played
	 * @param saveName
	 *            : name of the new file being added to the game
	 */
	private void saveAndCompress(Game game, String gameFolderPath, String saveName) {
		gameXMLFactory = new GameXMLFactory();
		setMinorGameXMLInfo(game);
		this.saveSong(gameFolderPath, game.getSongPath(), game.getName());
		this.saveLevels(gameFolderPath, game.getLevels());
		this.saveDefaults(gameFolderPath, game.getDefaults());
		this.saveDocument(gameFolderPath, saveName);
		this.zipDoc(gameFolderPath);
	}

	/*
	 * adds list of information to game xml document
	 * 
	 * @param game : game object being saved
	 * 
	 */
	private void setMinorGameXMLInfo(Game game) {

		gameXMLFactory.setName(game.getName());
		gameXMLFactory.addInfo(game.getInfo());
		gameXMLFactory.setTime(game.getCurrentTime());
		gameXMLFactory.setCountdown(game.getClockGoingDown());
		gameXMLFactory.setNumberOfLives(game.getNumberOfLives());
		gameXMLFactory.setUnlockedLevels(game.getUnlockedLevels());

	}

	/**
	 * Saves the document as a whole, after the XML serializing is done
	 * 
	 * @param gameFolderPath
	 *            : top-level directory of the game
	 */
	private void saveDocument(String gameFolderPath, String filename) {
		Document doc = gameXMLFactory.getDocument();
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, resourceManager.getXMLFormat());
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(gameFolderPath + File.separator + filename));
			transformer.transform(source, result);
		} catch (TransformerException e) {
		}
	}

	/**
	 * Saves the default entities into XML.
	 * 
	 * @param defaults
	 *            : List of entities that are defaults, to be saved into XML
	 * @param gameFolderPath
	 *            : top-level directory of the game
	 */
	private void saveDefaults(String gameFolderPath, List<Entity> defaults) {
		EntitySaver entitySaver = new EntitySaver(gameXMLFactory);
		List<Element> xmlDefaults = entitySaver.getEntityListAsXML(defaults, gameFolderPath);
		LevelSaver saver = new LevelSaver(gameXMLFactory);
		Element defaultsElement = saver.wrapEntityListInXMLTags(xmlDefaults);
		gameXMLFactory.addDefaultEntity(defaultsElement);
	}

	/**
	 * Saves the list of levels (list of entities) that will be written into
	 * XML.
	 * 
	 * @param levels
	 *            : list of levels to be written to XML
	 * @param gameFolderPath
	 *            : top-level directory of the game
	 */
	private void saveLevels(String gameFolderPath, List<Level> levels) {
		for (Level level : levels) {
			EntitySaver entitySaver = new EntitySaver(gameXMLFactory);
			List<Element> entityElements = entitySaver.getEntityListAsXML(level.getEntities(), gameFolderPath);
			Element cameraElement = entitySaver.getEntityAsXML(level.getCamera(), gameFolderPath);
			Element backgroundElement = entitySaver.getEntityAsXML(level.getBackground(), gameFolderPath);

			LevelSaver levelSaver = new LevelSaver(gameXMLFactory);
			Element levelElement = levelSaver.wrapLevelInXMLTags(entityElements, cameraElement, backgroundElement);
			gameXMLFactory.addLevel(levelElement);
		}
	}

	/**
	 * Saves the song path into the XML game file
	 * 
	 * @param gameFolderPath
	 *            : top-level directory of the game
	 * @param originalSongPath
	 *            : song path to be saved into XML
	 */
	private void saveSong(String gameFolderPath, String originalSongPath, String gameName) {
		if (originalSongPath.equals("")) {
			return;
		}
		try {
			String relativePath = resourceManager.getResourceTitle().toLowerCase() + File.separator + gameName
					+ resourceManager.getMP3();

			File originalSongFile = new File(originalSongPath);
			String savedSongPath = gameFolderPath + File.separator + relativePath;
			this.makeFile(savedSongPath);
			this.copyFileContents(originalSongFile, savedSongPath);
			gameXMLFactory.addSong(relativePath);
		} catch (IOException e) {
		}
	}

	/**
	 * Creates folder for game
	 * 
	 * @param gameFolderPath
	 *            location to save game folder
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

	/**
	 * Zips all files in the directory at the folder path
	 * 
	 * @param gameFolderPath
	 */
	private void zipDoc(String gameFolderPath) {
		List<File> toCompress = new ArrayList<File>();
		File dir = new File(gameFolderPath);
		File[] allFiles = dir.listFiles();
		if (allFiles != null) {
			for (File child : allFiles) {
				toCompress.add(child);
			}
		}
		try {
			Packager zipper = new Packager();
			zipper.packZip(new File(gameFolderPath + resourceManager.getVS()), toCompress);
		} catch (IOException e) {
		}
		this.deleteDir(dir);
	}

	/**
	 * Deletes directory, called after all files in directory are zipped to a
	 * single file
	 * 
	 * @param dir
	 * @return
	 */
	private boolean deleteDir(File dir) {
		dir.listFiles();
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					deleteDir(file);
				} else {
					file.delete();
				}
			}
		}
		return (dir.delete());
	}
}