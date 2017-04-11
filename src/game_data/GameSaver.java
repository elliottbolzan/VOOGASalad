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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import engine.Entity;
import engine.game.Level;

// Make references to paths relative. 

public class GameSaver {
	
	private Game game;

	public void saveGame(Game game, String filepath) {
		this.game = game;
		this.saveGame(game.getLevels(), filepath);
	}

	public void saveGame(List<Level> levels, String filePath) {
		createRoot(filePath);
		savelevels(levels, filePath + "/" + game.getName());
	}
	
	private void createRoot(String filePath) {
		File folder = new File(filePath + game.getName());
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

	private void savelevels(List<Level> levels, String filepath) {
		File entityfolder = new File(filepath + "/levels");
		if (!entityfolder.exists()) {
			entityfolder.mkdirs();
		}
		File dir = new File(filepath + "/levels");
		dir.mkdir();
		for (int i = 0; i < levels.size(); i++) {

			List<Entity> entities = new ArrayList<Entity>(levels.get(i).getEntities());
			List<String> entityfilepaths = new ArrayList<String>();

			for (int j = 0; j < entities.size(); j++) {
				Entity currentity = entities.get(j);
				String entityfilepath = saveEntity(currentity, filepath);
				entityfilepaths.add(entityfilepath);
			}

			LevelSaver ls = new LevelSaver(entityfilepaths, filepath, i);
			ls.saveLevel();
		}
	}

	public String saveEntity(Entity entity, String filepath) {
		File entityfolder = new File(filepath + "/entities");
		if (!entityfolder.exists()) {
			entityfolder.mkdirs();
		}
		String entityfilepath = "";
		try {
			saveEntityImage(entity, filepath);
			entityfilepath = filepath + "/entities/" + entity.getName() + ".xml";
			File entityfile = new File(entityfilepath);

			XStream xStream = new XStream(new DomDriver());
			xStream.registerConverter(new EntityConverter());
			String xmlstring = xStream.toXML(entity);
			FileWriter fw = new java.io.FileWriter(entityfile);

			fw.write(xmlstring);
			fw.close();

		} catch (IOException i) {
			i.printStackTrace();
		}

		return entityfilepath;
	}

	public void saveEntityImage(Entity entity, String filepath) {
		try {

			String sourcePath = new File(new URI(entity.getImagePath())).getAbsolutePath();
			Path sourcepath = Paths.get(sourcePath);

			String targetpathstring = filepath + "/images/" + entity.getName() + "image.png";
			File entityimagefile = new File(targetpathstring);

			entityimagefile.getParentFile().mkdirs();
			entityimagefile.createNewFile();

			Path targetpath = Paths.get(targetpathstring);
			Files.copy(sourcepath, targetpath, REPLACE_EXISTING);

		} catch (Exception i) {
			i.printStackTrace();
		}

	}
}
