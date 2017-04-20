package game_data;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GameXMLFactory
{

	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private Element nameNode;
	private Element levelsNode;
	private Element defaultsNode;
	private Element cameraNode;
	private Element resourceNode;

	/**
	 * GameXMLFactory constructor
	 */
	public GameXMLFactory() {
		initiate();
	}
	
	/**
	 * Initialize the XML file by creating the appropriate nodes
	 */
	private void initiate() {
		docFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			//TODO
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

		cameraNode = doc.createElement("Camera");
		rootElement.appendChild(cameraNode);
		
		resourceNode = doc.createElement("Resources");
		rootElement.appendChild(resourceNode);
	}
	
	/**
	 * Sets the name of the game in the XML file
	 * @param gameName
	 * 			game name to be set in XML
	 */
	public void setName(String gameName) {
		Attr attr = doc.createAttribute("GameName");
		attr.setValue(gameName);
		nameNode.setAttributeNode(attr);
	}

	/**
	 * Adds a level into the XML file given the level node
	 * @param levelInfo
	 * 			node to be added into XML
	 */
	public void addLevel(Element levelInfo) {
		Element newLevel = doc.createElement("level");
		Element importedLevelNode = (Element) doc.importNode(levelInfo, true);
		newLevel.appendChild(importedLevelNode);
		levelsNode.appendChild(newLevel);
	}

	/**
	 * Adds a song path into XML file given the string
	 * @param songPath : string song path to be added to XML
	 */
	public void addSong(String songPath) {
		Attr attr = doc.createAttribute("Song");
		attr.setValue(songPath);
		resourceNode.setAttributeNode(attr);
	}
	
	/**
	 * Adds a song path into XML file given the string
	 * @param songPath : string song path to be added to XML
	 */
	public void addCamera(Element cameraElement) {
		Element importedCameraNode = (Element) doc.importNode(cameraElement, true);
		cameraNode.appendChild(importedCameraNode);
	}

	/**
	 * Adds the default entities into XML given the element
	 * @param defaultEntity
	 * 			element to be added to the XML file
	 */
	public void addDefaultEntity(Element defaultEntity) {
		Element importedDefaultEntityNode = (Element) doc.importNode(defaultEntity, true);
		defaultsNode.appendChild(importedDefaultEntityNode);
	}

	/**
	 * Adds the entity info into XML
	 * @param element
	 * 			element node to be added
	 * @param entityInfo
	 * 			entity info to be added into XML
	 */
	public void addEntityInfotoElement(Element element, Element entityInfo) {
		Element newEntity = doc.createElement("entity");
		newEntity.appendChild(entityInfo);
		element.appendChild(newEntity);
	}

	/**
	 * Converts given string into an XML element node
	 * @param xmlString
	 * 			string to be converted into XML element
	 * @return
	 */
	public Element stringToElement(String xmlString) {
		try {
			InputSource is = new InputSource(new ByteArrayInputStream(xmlString.getBytes()));
			is.setEncoding("UTF-8");
			return DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(is).getDocumentElement();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * getter for document containing XML file
	 * @return
	 */
	public Document getDocument() {
		return doc;
	}

}