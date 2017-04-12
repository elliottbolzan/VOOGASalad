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
	
	
	
	public LevelSaver(List<Element> inputentitynodes){
		entityNodes=inputentitynodes;
	}


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
		//writeContent(levelnumber);

	}
	
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
	
	private void addLevelEntity(int entityNumber, Element entityNode, Element rootElement){


		Element entity = doc.createElement("Entity");
		rootElement.appendChild(entity);
		Element importedEntityNode= (Element) doc.importNode(entityNode, true);
		entity.appendChild(importedEntityNode);

	}


}
