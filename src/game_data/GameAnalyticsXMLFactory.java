package game_data;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GameAnalyticsXMLFactory extends XMLFactory{
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private Element playsNode;
	private Element winsNode;
	private Element lossNode;
	private Element scoreNode;
	private ResourceManager rm;
	
	
	/**
	 * GameXMLFactory constructor
	 */
	public GameAnalyticsXMLFactory() {
		initiate();
	}
	
	/**
	 * Initialize the XML file by creating the appropriate nodes
	 */
	private void initiate() {
		//rm = new AnalyticsResourceManager();
		System.out.println("buildingdoc");
		docFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			//TODO
		}
		doc = docBuilder.newDocument();

		Element rootElement = doc.createElement("AnalyticsData");
		doc.appendChild(rootElement);

		playsNode = doc.createElement("Plays");
		rootElement.appendChild(playsNode);
	
		winsNode = doc.createElement("Wins");
		rootElement.appendChild(winsNode);

		lossNode = doc.createElement("Losses");
		rootElement.appendChild(lossNode);

		scoreNode = doc.createElement("Scores");
		rootElement.appendChild(scoreNode);
		
		initiateNodes();
	}
	
	/**
	 * Initialize individual nodes within XML Document
	 */
	private void initiateNodes(){
		initiatePlayNode();
		initiateWinsNode();
		initiateLossesNode();
	}
	
	/**
	 * Initialize play node within XML Document
	 */
	private void initiatePlayNode(){
		Attr attr = doc.createAttribute("NumberofPlays");
		attr.setValue(Integer.toString(0));
		playsNode.setAttributeNode(attr);
	}
	/**
	 * Initialize wins node within XML Document
	 */
	private void initiateWinsNode(){
		Attr attr = doc.createAttribute("NumberofWins");
		attr.setValue(Integer.toString(0));
		winsNode.setAttributeNode(attr);
	}
	/**
	 * Initialize loss node within XML Document
	 */
	private void initiateLossesNode(){
		Attr attr = doc.createAttribute("NumberofLosses");
		attr.setValue(Integer.toString(0));
		lossNode.setAttributeNode(attr);
	}
	
	
	/**
	 * returns the document
	 */
	public Document getDocument(){
		return doc;
	}
	
	
}
