package game_data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import engine.game.Level;

// Load process: read (relative) path from file.
// Create an absolute path from it.
// Give Entities the absolute paths.

public class GameLoader {

	
	public Game loadGame(String folderPath) throws NotAGameFolderException{
	
		File levelFolder = new File(folderPath + "/settings.xml");
		if (!levelFolder.exists()) {
			throw new NotAGameFolderException();
		}
		Document doc=null;
		try{
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	      DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	      doc = docBuilder.parse(folderPath+"/settings.xml");
		}catch (Exception e){
			e.printStackTrace();
		}
		Game game = new Game();
		addName(game,doc);
		addLevels(game,doc);
		addDefaults(game,doc);
		return game;
	}
	
	private void addDefaults(Game game, Document doc){
		
		NodeList defaultsNode = doc.getElementsByTagName("Defaults");
		Element entitiesNode= (Element) defaultsNode.item(0).getChildNodes().item(0);
		
		NodeList entitiesList = entitiesNode.getChildNodes();
		List<Entity> defaultlist = new ArrayList<Entity>();
		for(int i=0;i<entitiesList.getLength();i++){
			if(entitiesList.item(i).getNodeName().equals("Entity")){
				Entity instantiatedEntity = getEntityFromElement( (Element) entitiesList.item(i));
				defaultlist.add(instantiatedEntity);
			}
		}
		game.setDefaults(defaultlist);
		
	}
	private void addName(Game game, Document doc){
		
		
		
		NodeList nameNodes =doc.getElementsByTagName("Name");
		game.setName(nameNodes.item(0).getAttributes().item(0).getNodeValue());

	}
	
	private  void addLevels(Game game,Document doc){
		
		NodeList levelsNode = doc.getElementsByTagName("Levels");
		NodeList levelsList = levelsNode.item(0).getChildNodes();
		List<Level> gameLevels  = new ArrayList<Level>();
		
	
		for (int i=0;i<levelsList.getLength();i++){
			
			Element levelElement= (Element) levelsList.item(i);
			Level instantiatedLevel=convertElementtoLevel(levelElement);
			gameLevels.add(instantiatedLevel);
			
		}
		game.setLevels(gameLevels);
		
	}
	
	private Level convertElementtoLevel(Element levelElement){
		
		Element entitiesNode = (Element) levelElement.getChildNodes().item(0);
		
		NodeList entities = entitiesNode.getChildNodes();
		Level returnedLevel = new Level();
		for (int i=0;i<entities.getLength();i++){
			if(entities.item(i).getNodeName().equals("Entity")){
				Entity instantiatedEntity = getEntityFromElement( (Element) entities.item(i) );
				returnedLevel.addEntity(instantiatedEntity);
			}
		}
		
		return returnedLevel;
		
	}
	
	private Entity getEntityFromElement(Element entityElement){
		
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new EntityConverter());
		Entity entity = (Entity) xStream.fromXML(getXMLStringFromElement(entityElement));
		
		return entity;
		
	}
	
	//http://stackoverflow.com/questions/32739278/convert-elementorg-w3c-dom-to-string-in-java
	
	private String getXMLStringFromElement(Element entityElement){
		StreamResult result =null;
		NodeList entityChildren =entityElement.getChildNodes();
		Element entityXMLElement= null;
		for(int i=0;i<entityChildren.getLength();i++){
			if(entityChildren.item(i).getNodeType()==Node.ELEMENT_NODE){
				entityXMLElement= (Element) entityChildren.item(i);
			}
		}
		try{
		TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(entityXMLElement);
        result = new StreamResult(new StringWriter());
			transformer.transform(source, result);
		}
		catch (TransformerException e) {
			e.printStackTrace();
		}
        String strObject = result.getWriter().toString();
		return strObject;
		
	}
	
}
