package game_data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import engine.Entity;
import engine.game.Level;

// Load process: read (relative) path from file.
// Create an absolute path from it.
// Give Entities the absolute paths.

public class GameLoader {

	public List<Level> loadGame(String folderpath) throws NotAGameFolderException {

		File levelfolder = new File(folderpath + "/levels");
		if (!levelfolder.exists()) {
			throw new NotAGameFolderException();
		}
		File[] levelfiles = levelfolder.listFiles();

		List<Level> levels = new ArrayList<Level>();
		for (int i = 0; i < levelfiles.length; i++) {
			File levelfile = levelfiles[i];
			Level neolevel = new Level();
			addEntitiestoLevel(neolevel, levelfile);
			levels.add(neolevel);
		}

		return levels;

	}

	private void addEntitiestoLevel(Level neolevel, File levelfile) {

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(levelfile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("path");
			for (int i = 0; i < nList.getLength(); i++) {
				String entitypath = nList.item(i).getTextContent();
				Entity entity = getEntityFromFilePath(entitypath);
				neolevel.addEntity(entity);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Entity getEntityFromFilePath(String entitypath) {
		Entity entity = null;
		try {

			String entityxmlstring = getXMLStringFromEntityPath(entitypath);
			XStream xStream = new XStream(new DomDriver());
			xStream.registerConverter(new EntityConverter());

			entity = (Entity) xStream.fromXML(entityxmlstring);
		} catch (Exception i) {
			i.printStackTrace();
		}
		return entity;
	}

	// http://stackoverflow.com/questions/5511096/java-convert-formatted-xml-file-to-one-line-string
	private String getXMLStringFromEntityPath(String entitypath) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(entitypath)));
			String line;

			while ((line = br.readLine()) != null) {
				sb.append(line.trim());
			}

			br.close();
		} catch (Exception i) {
			i.printStackTrace();
		}

		return sb.toString();
	}

}
