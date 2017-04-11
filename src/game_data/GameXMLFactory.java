package game_data;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GameXMLFactory {


	private List<String> entityfilepaths;
	private String folderpath;
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
	
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
		
		}
	
	private void addName(String gamename){
		
		Element nameElement = doc.createElement("Name");
		
		
		Attr attr = doc.createAttribute("GameName");
		attr.setValue(gamename);
		nameElement.setAttributeNode(attr);
		
		doc.appendChild(nameElement);
	}
	
	
	
	
	
	
	
	
}
