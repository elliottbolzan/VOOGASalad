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
		gameXMLFactory= new GameXMLFactory();
		gameXMLFactory.setName(game.getName());
		createRoot(filepath);
		saveLevels(game.getLevels(), filepath + "/" + game.getName());
		saveDefaults(game.getDefaults(), filepath + "/" + game.getName());
		saveSong(filepath+game.getSongPath());
		saveDocument(filepath);
	}
	
	
	
	private void createRoot(String filePath) {	
		File folder = new File(filePath +"/" + game.getName());
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}
	
	private void saveSong(String songPath){
		//System.out.println(songPath);
		//LevelSaver ls = new LevelSaver(null);
		//String xmlsong = ls.saveSong(songPath);
		//Element songelement = gameXMLFactory.stringToElement(xmlSong);
		gameXMLFactory.addSong(songPath);
		
		//gameXMLFactory.addSong(gameXMLFactory.stringToElement(songPath));
	}
	
	
	public void saveEntityImage(Entity entity, String filePath) {
		try {
			String sourcePathString = filePath;
			Path sourcePath = Paths.get(sourcePathString);
			String targetPathString = filePath + "/images/" + entity.getName() + "image.png";
			File entityImageFile = new File(targetPathString);
			entityImageFile.getParentFile().mkdirs();
			entityImageFile.createNewFile();
			Path targetPath = Paths.get(targetPathString);
			Files.copy(sourcePath, targetPath, REPLACE_EXISTING);
		} catch (Exception i) {
			i.printStackTrace();
		}
	}
	
	
	
	private void saveDefaults(List<Entity> defaults, String filePath){
		
		
			List<Entity> entities = defaults;
			List<Element> entityNodes = new ArrayList<Element>();
		
			for (int j = 0; j < entities.size(); j++) {
				Entity currEntity = entities.get(j);
				
				Element entityNode = getEntityNode(currEntity);
				entityNodes.add(entityNode);
			}
			LevelSaver ls = new LevelSaver(entityNodes);
			String xmlLevel = ls.saveLevel();
			//System.out.println(xmlLevel);
			Element levelElement = gameXMLFactory.stringToElement(xmlLevel);
			gameXMLFactory.addDefaultEntity(levelElement);
			//System.out.println(xmlLevel);
			
			
		
		
		
	}
	
	private void saveLevels(List<Level> levels, String filePath) {
		for (int i = 0; i < levels.size(); i++) {
			List<Entity> entities = new ArrayList<Entity>(levels.get(i).getEntities());
			List<Element> entityNodes = new ArrayList<Element>();
		
			for (int j = 0; j < entities.size(); j++) {

				Entity currEntity = entities.get(j);				

				Element entityNode = getEntityNode(currEntity);
				entityNodes.add(entityNode);
			}
			LevelSaver ls = new LevelSaver(entityNodes);
			String xmlLevel = ls.saveLevel();
		//	System.out.println(xmlLevel);
			Element levelElement = gameXMLFactory.stringToElement(xmlLevel);
			gameXMLFactory.addLevel(levelElement);
			//System.out.println(xmlLevel);
			
			
		}
		
		
		
	}
	
	
	private Element getEntityNode(Entity entity){
		
		
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new EntityConverter());
		String xmlString = xStream.toXML(entity);
		
		return gameXMLFactory.stringToElement(xmlString);
		
	}
	
	private void saveDocument(String filePath){
		
		Document doc =gameXMLFactory.getDocument();
		
		File levelDirectory = new File(filePath);
		if(!levelDirectory.exists()){
			levelDirectory.mkdirs();
		}
		try{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(new File(filePath+"/"+game.getName()+"/settings.xml"));
			transformer.transform(source, result);
		}
		catch (TransformerException e){
			e.printStackTrace();
		}
	}
}