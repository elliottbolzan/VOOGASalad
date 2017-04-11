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

	private List<Element> entitynodes;
	private String folderpath;
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private int levelnumber;
	public LevelSaver(List<Element> inputentitynodes, String inputfolderpath,int inputlevelnumber){
		entitynodes=inputentitynodes;
		folderpath=inputfolderpath;
		levelnumber=inputlevelnumber;

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


		for(int i=0;i<entitynodes.size();i++){
			addLevelEntity(i+1, entitynodes.get(i),rootElement);
		}

		return toString(doc);
		//writeContent(levelnumber);

	}
	
	private String toString(Document doc) {
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
	
	private void addLevelEntity(int entitynumber, Element entitynode, Element rootElement){


		Element entity = doc.createElement("Entity");
		rootElement.appendChild(entity);
/*
		Attr attr = doc.createAttribute("id");
		attr.setValue(Integer.toString(entitynumber));
		levelpath.setAttributeNode(attr);

		Element pathname = doc.createElement("path");
		pathname.appendChild(doc.createTextNode(entityfilepath));
		levelpath.appendChild(pathname);
		*/
		Element importedentitynode= (Element) doc.importNode(entitynode, true);
		entity.appendChild(importedentitynode);

	}


	private void writeContent(int levelnumber){


		File leveldirectory = new File(folderpath+"/levels");
		if(!leveldirectory.exists()){
			leveldirectory.mkdirs();
		}


		try{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(folderpath+"/levels/level"+levelnumber+".xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);


		}
		catch (TransformerException e){
			e.printStackTrace();
		}

	}
}
