package game_data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
	private Element defaultsNode;
	
	//Name
	//Entities
	//Levels
	//Defaults
	//Song
	
	
	public GameXMLFactory(){
		initiate();
	}
	private void initiate(){
		
		docFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		doc = docBuilder.newDocument();
		
		Element rootElement = doc.createElement("Game");
		doc.appendChild(rootElement);
		
		nameNode = doc.createElement("Name");
		rootElement.appendChild(nameNode);
		
		levelsNode = doc.createElement("Levels");
		rootElement.appendChild(levelsNode);
		
		defaultsNode = doc.createElement("Defaults");
		rootElement.appendChild(defaultsNode);
		}
	
	public void setName(String gamename){
		Attr attr = doc.createAttribute("GameName");
		attr.setValue(gamename);
		nameNode.setAttributeNode(attr);

	}
	
	
	public void addLevel(Element levelinfo){
		
		
		
		Element newlevel = doc.createElement("level");
		
		Element importedlevelnode=(Element) doc.importNode(levelinfo, true);
		newlevel.appendChild(importedlevelnode);
		levelsNode.appendChild(newlevel);
		
	}
	public void addDefaultEntity(Element defaultentity){
		

		
		Element importeddefaultentitynode= (Element) doc.importNode(defaultentity, true);
	
		defaultsNode.appendChild(importeddefaultentitynode);
		
	}
	public void addEntityInfotoElement(Element element, Element entityinfo){
		
		
		Element newentity = doc.createElement("entity");
		newentity.appendChild(entityinfo);
		
		element.appendChild(newentity);
		
	}
	
	
	public Element stringToElement(String xmlstring){
		
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
	
	public Document getDocument(){
		return doc;
	}
	
}
