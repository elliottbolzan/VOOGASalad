package game_data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class GameXMLFactory {


	private List<String> entityfilepaths;
	private String folderpath;
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private Element nameNode;
	private Element levelsNode;
	
	//Name
	//Entities
	//Levels
	//Defaults
	//Song
	
	private void initiate(){
		
		docFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		doc = docBuilder.newDocument();
		Element nameNode = doc.createElement("Name");
		doc.appendChild(nameNode);
		
		Element levelsNode = doc.createElement("Levels");
		doc.appendChild(nameNode);
		}
	
	private void setName(String gamename){
		Attr attr = doc.createAttribute("GameName");
		attr.setValue(gamename);
		nameNode.setAttributeNode(attr);

	}
	
	
	private void addLevel(Element levelinfo){
		
		
		
		Element newlevel = doc.createElement("level");
		newlevel.appendChild(levelinfo);
		
		
		levelsNode.appendChild(newlevel);
		
	}
	
	private void addEntityInfotoElement(Element element, Element entityinfo){
		
		
		Element newentity = doc.createElement("entity");
		newentity.appendChild(entityinfo);
		
		element.appendChild(newentity);
		
	}
	
	
	private Element stringToElement(String xmlstring){
		
		try {
			return  DocumentBuilderFactory
				    .newInstance()
				    .newDocumentBuilder()
				    .parse(new ByteArrayInputStream(xmlstring.getBytes()))
				    .getDocumentElement();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
