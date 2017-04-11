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
	private GameXMLFactory gamexmlfactory;

	public void saveGame(Game game, String filepath) {
		this.game = game;
		gamexmlfactory= new GameXMLFactory();
		this.saveGame(game.getLevels(), filepath);
		saveDocument(filepath);
	}

	public void saveGame(List<Level> levels, String filePath) {
		createRoot(filePath);
		savelevels(levels, filePath + "/" + game.getName());
		savedefaults(game.getDefaults(), filePath + "/" + game.getName());
		
	}
	
	private void createRoot(String filePath) {
		
		System.out.println(filePath+game.getName());
		File folder = new File(filePath + game.getName());
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}
	
	private void savedefaults(List<Entity> levels, String filepath){
		
	}
	
	private void savelevels(List<Level> levels, String filepath) {
		for (int i = 0; i < levels.size(); i++) {

			List<Entity> entities = new ArrayList<Entity>(levels.get(i).getEntities());
			List<Element> entitynodes = new ArrayList<Element>();
		
			for (int j = 0; j < entities.size(); j++) {
				Entity currentity = entities.get(j);
				
				
				Element entitynode = getEntityNode(currentity);
				entitynodes.add(entitynode);
			}

			LevelSaver ls = new LevelSaver(entitynodes, filepath, i);
			String xmlLevel = ls.saveLevel();
			System.out.println(xmlLevel);
			Element levelelement = gamexmlfactory.stringToElement(xmlLevel);
			gamexmlfactory.addLevel(levelelement);
			System.out.println(xmlLevel);
			
			
		}
		
		
		
//		File entityfolder = new File(filepath + "/levels");
//		if (!entityfolder.exists()) {
//			entityfolder.mkdirs();
//		}
//		File dir = new File(filepath + "/levels");
//		dir.mkdir();
//		for (int i = 0; i < levels.size(); i++) {
//
//			List<Entity> entities = new ArrayList<Entity>(levels.get(i).getEntities());
//			List<String> entityfilepaths = new ArrayList<String>();
//
//			for (int j = 0; j < entities.size(); j++) {
//				Entity currentity = entities.get(j);
//				String entityfilepath = saveEntity(currentity, filepath);
//				entityfilepaths.add(entityfilepath);
//			}
//
//			LevelSaver ls = new LevelSaver(entityfilepaths, filepath, i);
//			ls.saveLevel();
//		}
	}

	
	
	private Element getEntityNode(Entity entity){
		
		
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new EntityConverter());
		String xmlstring = xStream.toXML(entity);
		
		return gamexmlfactory.stringToElement(xmlstring);
		
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
			
			
			
			String sourcePath = filepath;
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
	

	private void saveDocument(String filepath){
		
		Document doc =gamexmlfactory.getDocument();
		
		File leveldirectory = new File(filepath);
		if(!leveldirectory.exists()){
			leveldirectory.mkdirs();
		}


		try{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath+"gamexmlfactorytest.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);


		}
		catch (TransformerException e){
			e.printStackTrace();
		}
	}
}
