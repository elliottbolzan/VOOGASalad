package game_data;

import java.util.List;

import org.w3c.dom.Element;

/**
 * @author jaydoherty
 *
 */
public class LevelSaver {

	private GameXMLFactory gameXMLFactory;
	
	public LevelSaver(GameXMLFactory gameXMLFactory) {
		this.gameXMLFactory = gameXMLFactory;
	}

	/**
	 * 
	 * @return
	 */
	protected Element wrapLevelInXMLTags(List<Element> entityList, Element camera) {
		Element levelElement = gameXMLFactory.getDocument().createElement("Level");
		Element entitiesElement = this.createDocElement("Entities", levelElement);
		
		for (Element entityElement : entityList) {
			this.importElement("Entity", entityElement, entitiesElement);
		}
		
		if(camera != null) {
			this.importElement("Camera", camera, levelElement);
		}	
			
		return levelElement;
	}
	
	private void importElement(String tagName, Element elementToImport, Element parentToAppend) {
		Element wrapper = this.createDocElement(tagName, parentToAppend);
		Element importedEntity = (Element) gameXMLFactory.getDocument().importNode(elementToImport, true);
		wrapper.appendChild(importedEntity);
	}
	
	private Element createDocElement(String tagName, Element parentToAppend) {
		Element newDocElement = gameXMLFactory.getDocument().createElement(tagName);
		parentToAppend.appendChild(newDocElement);
		return newDocElement;
	}
}
