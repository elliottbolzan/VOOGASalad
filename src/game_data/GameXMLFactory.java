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

	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private Element nameNode;
	private Element levelsNode;
	private Element defaultsNode;
	private Element resourceNode;

	public GameXMLFactory() {
		initiate();
	}

	private void initiate() {

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

		resourceNode = doc.createElement("Resources");
		rootElement.appendChild(resourceNode);
	}

	public void setName(String gameName) {
		Attr attr = doc.createAttribute("GameName");
		attr.setValue(gameName);
		nameNode.setAttributeNode(attr);
	}

	public void addLevel(Element levelInfo) {

		Element newLevel = doc.createElement("level");
		Element importedLevelNode = (Element) doc.importNode(levelInfo, true);
		newLevel.appendChild(importedLevelNode);
		levelsNode.appendChild(newLevel);

	}

	public void addSong(String songPath) {
		Attr attr = doc.createAttribute("Song");
		attr.setValue(songPath);
		resourceNode.setAttributeNode(attr);
	}

	public void addDefaultEntity(Element defaultEntity) {

		Element importedDefaultEntityNode = (Element) doc.importNode(defaultEntity, true);
		defaultsNode.appendChild(importedDefaultEntityNode);

	}

	public void addEntityInfotoElement(Element element, Element entityInfo) {

		Element newEntity = doc.createElement("entity");
		newEntity.appendChild(entityInfo);
		element.appendChild(newEntity);

	}

	public Element stringToElement(String xmlString) {

		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(xmlString.getBytes())).getDocumentElement();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Document getDocument() {
		return doc;
	}

}
