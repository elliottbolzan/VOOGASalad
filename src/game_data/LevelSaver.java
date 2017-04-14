package game_data;
import java.io.File;
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
public class LevelSaver {

	private List<Element> entityNodes;
	private String folderPath;
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
	/**
	 * Constructor of levelsaver, giving the input elements
	 * @param inputentitynodes
	 * 		initialize entity nodes to this
	 */
	public LevelSaver(List<Element> inputentitynodes){
		entityNodes=inputentitynodes;
	}

	/**
	 * Saves the level and returns the string from the document builder
	 * @return
	 */
	public String saveLevel(){
		docFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("Entities");
		doc.appendChild(rootElement);
		for(int i=0;i<entityNodes.size();i++){
			addLevelEntity(i+1, entityNodes.get(i),rootElement);
		}
		return toString(doc);
	}
	
	/**
	 * Saves the song string path and returns the document string
	 * @param songPath
	 * 			song path to be saved
	 * @return
	 */
	public String saveSong(String songPath){
		docFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		doc = docBuilder.newDocument();
		
		Element rootsong = doc.createElement("songpath");
		doc.appendChild(rootsong);
		rootsong.setAttribute("songpath", songPath);
		
		return toString(doc);
	}
	
	/**
	 * Helper method to convert a Document into an XML string
	 * @param doc
	 * @return
	 */
	public String toString(Document doc) {
	    try {
	        StringWriter sw = new StringWriter();
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

	        transformer.transform(new DOMSource(doc), new StreamResult(sw));
	        return sw.toString();
	    } catch (Exception ex) {
	        throw new RuntimeException("Error converting to String", ex);
	    }
	}
	
	/**
	 * Adds a level entity from the entity element node (into the XML file)
	 * @param entityNumber
	 * 				the number of the entity
	 * @param entityNode
	 * 				element node to be written into XML
	 * @param rootElement
	 * 				rootelement to be used to append the entity too
	 */
	private void addLevelEntity(int entityNumber, Element entityNode, Element rootElement){
		Element entity = doc.createElement("Entity");
		rootElement.appendChild(entity);
		Element importedEntityNode= (Element) doc.importNode(entityNode, true);
		entity.appendChild(importedEntityNode);
	}
}
