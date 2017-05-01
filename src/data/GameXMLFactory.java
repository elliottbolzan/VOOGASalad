package data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * This class is an XML file maker that represents a single game object
 * 
 * @author Michael Li
 * 
 *
 */
public class GameXMLFactory
{


	private Document doc;
	private Map<String,Element> elementMap;
	private Element rootElement;
	private ResourceManager rm;

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
		rm = new ResourceManager();
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		}
		doc = docBuilder.newDocument();
		
		rootElement = doc.createElement(rm.getGameTitle());
		doc.appendChild(rootElement);
		
		elementMap  = new HashMap<String,Element>();
		initiateRoot();
		
		};
	
	
	private void initiateRoot(){
		addToRoot(rm.getNameTitle());
		addToRoot(rm.getLevelsTitle());
		addToRoot(rm.getDefaultsTitle());
		addToRoot(rm.getCameraTitle());
		addToRoot(rm.getResourceTitle());
		addToRoot(rm.getInfoTitle());
		addToRoot(rm.getTimeTitle());
		addToRoot(rm.getCountdownTitle());
		addToRoot(rm.getLivesTitle());
		addToRoot(rm.getUnlockedLevelsTitle());
	}
	
	private void addToRoot(String elementName){
		Element instantiatedElement = doc.createElement(elementName);
		elementMap.put(elementName, instantiatedElement);
		rootElement.appendChild(instantiatedElement);
	}
	
	
	/**
	 * Sets the number of lives of the game in the XML file
	 * @param numberOfLives
	 * 				lives to be set in XML
	 */
	public void setNumberOfLives(int numberOfLives) {
		Attr attr = doc.createAttribute("LivesAmount");
		attr.setValue( Integer.toString(numberOfLives));
		elementMap.get(rm.getLivesTitle()).setAttributeNode(attr);
	}

	/**
	 * Sets the number of lives of the game in the XML file
	 * @param numberOfLives
	 * 				lives to be set in XML
	 */
	public void setUnlockedLevels(Set<Integer> unlockedLevels) {
		
		for(Integer key : unlockedLevels){
		Element unlockedLevel = doc.createElement(rm.getUnlockedLevelsElement());
		Attr attr=doc.createAttribute(rm.getUnlockedLevelsAttribute());
		attr.setValue(key.toString());
		unlockedLevel.setAttributeNode(attr);
		elementMap.get(rm.getUnlockedLevelsTitle()).appendChild(unlockedLevel);
		}
	}
	
	
	
	
	/**
	 * Sets the current time of the game in the XML file
	 * @param currentTime
	 * 				time to be set in XML
	 */
	public void setTime(double gameTime) {
		Attr attr = doc.createAttribute("ClockTime");
		attr.setValue( Double.toString(gameTime));
		elementMap.get(rm.getTimeTitle()).setAttributeNode(attr);
	}
	
	

	/**
	 * Sets the current time of the game in the XML file
	 * @param currentTime
	 * 				time to be set in XML
	 */
	public void setCountdown(boolean clockGoingDown) {
		Attr attr = doc.createAttribute("CountingDown");
		attr.setValue(Boolean.toString(clockGoingDown));
		elementMap.get(rm.getCountdownTitle()).setAttributeNode(attr);
	}
	
	
	/**
	 * Sets the name of the game in the XML file
	 * @param gameName
	 * 			game name to be set in XML
	 */
	public void setName(String gameName) {
		Attr attr = doc.createAttribute(rm.getNameAttribute());
		attr.setValue(gameName);
		elementMap.get(rm.getNameTitle()).setAttributeNode(attr);
	}

	/**
	 * Adds a level into the XML file given the level node
	 * @param levelInfo
	 * 			node to be added into XML
	 */
	public void addLevel(Element levelInfo) {
		elementMap.get(rm.getLevelsTitle()).appendChild(levelInfo);
	}

	/**
	 * Adds a song path into XML file given the string
	 * @param songPath : string song path to be added to XML
	 */
	public void addSong(String songPath) {
		Attr attr = doc.createAttribute(rm.getSongResourceElement());
		attr.setValue(songPath);
		elementMap.get(rm.getResourceTitle()).setAttributeNode(attr);
	}
	
	/**
	 * 
	 * @param info
	 */
	
	public void addInfo(String info){
		Attr attr = doc.createAttribute(rm.getInfoAttribute());
		attr.setValue(info);
		elementMap.get(rm.getInfoTitle()).setAttributeNode(attr);
	}
	
	/**
	 * Adds a song path into XML file given the string
	 * @param songPath : string song path to be added to XML
	 */
	public void addCamera(Element cameraElement) {
		Element importedCameraNode = (Element) doc.importNode(cameraElement, true);
		elementMap.get(rm.getCameraTitle()).appendChild(importedCameraNode);
	}

	
	/**
	 * Adds the default entities into XML given the element
	 * @param defaultEntity
	 * 			element to be added to the XML file
	 */
	public void addDefaultEntity(Element defaultEntity) {
		Element importedDefaultEntityNode = (Element) doc.importNode(defaultEntity, true);
		elementMap.get(rm.getDefaultsTitle()).appendChild(importedDefaultEntityNode);
	}

	/**
	 * Adds the entity info into XML
	 * @param element
	 * 			element node to be added
	 * @param entityInfo
	 * 			entity info to be added into XML
	 */
	public void addEntityInfotoElement(Element element, Element entityInfo) {
		Element newEntity = doc.createElement(rm.getEntityElement());
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
		
		} catch (IOException e) {
			
		} catch (ParserConfigurationException e) {
		
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