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
	private Element achieveNode;
	private Element backgroundNode;
	private Element infoNode;
	private Element timeNode;
	private Element countdownNode;
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
		
		docFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			//TODO
		}
		doc = docBuilder.newDocument();
		
		Element rootElement = doc.createElement(rm.getGameTitle());
		doc.appendChild(rootElement);
		
		nameNode = doc.createElement(rm.getNameTitle());
		rootElement.appendChild(nameNode);
		
		levelsNode = doc.createElement(rm.getLevelsTitle());
		rootElement.appendChild(levelsNode);

		defaultsNode = doc.createElement(rm.getDefaultsTitle());
		rootElement.appendChild(defaultsNode);

		cameraNode = doc.createElement(rm.getCameraTitle());
		rootElement.appendChild(cameraNode);
		
		resourceNode = doc.createElement(rm.getResourceTitle());
		rootElement.appendChild(resourceNode);
		
//		achieveNode = doc.createElement("Achievements");
//		rootElement.appendChild(achieveNode);
//		
//		backgroundNode = doc.createElement("Background");
//		rootElement.appendChild(backgroundNode);
		
		infoNode = doc.createElement("GameInfo");
		rootElement.appendChild(infoNode);
		
		timeNode = doc.createElement("CurrentTime");
		rootElement.appendChild(timeNode);
		
		countdownNode = doc.createElement("TimeGoingDown");
		rootElement.appendChild(countdownNode);
	}
	
	
	/**
	 * Sets the current time of the game in the XML file
	 * @param currentTime
	 * 				time to be set in XML
	 */
	public void setTime(double gameTime) {
		Attr attr = doc.createAttribute("ClockTime");
		attr.setValue( Double.toString(gameTime));
		timeNode.setAttributeNode(attr);
	}
	
	

	/**
	 * Sets the current time of the game in the XML file
	 * @param currentTime
	 * 				time to be set in XML
	 */
	public void setCountdown(boolean clockGoingDown) {
		Attr attr = doc.createAttribute("CountingDown");
		attr.setValue(Boolean.toString(clockGoingDown));
		countdownNode.setAttributeNode(attr);
	}
	
	
	/**
	 * Sets the name of the game in the XML file
	 * @param gameName
	 * 			game name to be set in XML
	 */
	public void setName(String gameName) {
		Attr attr = doc.createAttribute(rm.getNameAttribute());
		attr.setValue(gameName);
		nameNode.setAttributeNode(attr);
	}

	/**
	 * Adds a level into the XML file given the level node
	 * @param levelInfo
	 * 			node to be added into XML
	 */
	public void addLevel(Element levelInfo) {
		levelsNode.appendChild(levelInfo);
	}

	/**
	 * Adds a song path into XML file given the string
	 * @param songPath : string song path to be added to XML
	 */
	public void addSong(String songPath) {
		Attr attr = doc.createAttribute(rm.getSongResourceElement());
		attr.setValue(songPath);
		resourceNode.setAttributeNode(attr);
	}
	
	/**
	 * 
	 * @param achieve
	 */
	public void addAchievement(String achieve){
		Attr attr = doc.createAttribute("Achievement");
		attr.setValue(achieve);;
		achieveNode.setAttributeNode(attr);
	}
	
	/**
	 * 
	 * @param info
	 */
	public void addInfo(String info){
		Attr attr = doc.createAttribute("Info");
		attr.setValue(info);
		infoNode.setAttributeNode(attr);
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
	 * 
	 * @param backPath
	 */
	public void addBackground(String backPath){
		Attr attr = doc.createAttribute("Background");
		attr.setValue(backPath);
		backgroundNode.setAttributeNode(attr);
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