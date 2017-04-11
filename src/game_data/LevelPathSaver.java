package game_data;


import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LevelPathSaver {


	private List<String> filepaths;
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private String rootfolder;



	public LevelPathSaver(List<String> inputfilepaths,String inputrootfolder){
		filepaths=inputfilepaths;
		rootfolder= inputrootfolder;
	}


	public void saveLevelPaths(){

		docFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		doc = docBuilder.newDocument();


		Element rootElement = doc.createElement("FilePaths");
		doc.appendChild(rootElement);


		for(int i=0;i<filepaths.size();i++){
			addLevelPath(i+1, filepaths.get(i),rootElement);
		}



		writeContent(rootfolder + "/levelpaths.xml");

	}


	public void addLevelPath(int level, String filepath, Element rootElement){
		Element levelpath = doc.createElement("LevelPath");
		rootElement.appendChild(levelpath);

		Attr attr = doc.createAttribute("id");
		attr.setValue(Integer.toString(level));
		levelpath.setAttributeNode(attr);

		Element pathname = doc.createElement("path");
		pathname.appendChild(doc.createTextNode(filepath));
		levelpath.appendChild(pathname);
	}




	private void writeContent(String currfilepath){
		System.out.println(currfilepath);
		try{

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(currfilepath));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		}
		catch (TransformerException e){
			e.printStackTrace();
		}

	}

}