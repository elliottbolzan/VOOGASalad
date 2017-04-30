package data;

import java.util.List;

import org.w3c.dom.Element;

/**
 * This class provides helper methods for wrapping lists of Entities or entire Levels in the 
 * proper XML tags. 
 * 
 * @author Jay Doherty
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
	protected Element wrapLevelInXMLTags(List<Element> entityList, Element camera, Element background) {

		Element levelElement = gameXMLFactory.getDocument().createElement("Level");
		Element entitiesElement = this.wrapEntityListInXMLTags(entityList);
		levelElement.appendChild(entitiesElement);

		this.importElement("Camera", camera, levelElement);
		this.importElement("Background", background, levelElement);

		return levelElement;
	}

	protected Element wrapEntityListInXMLTags(List<Element> entityList) {
		Element entitiesElement = gameXMLFactory.getDocument().createElement("Entities");

		for (Element entityElement : entityList) {
			this.importElement("Entity", entityElement, entitiesElement);
		}

		return entitiesElement;
	}

	private void importElement(String tagName, Element elementToImport, Element parentToAppend) {
		Element tags = this.createDocElement(tagName, parentToAppend);
		Element importedEntity = (Element) gameXMLFactory.getDocument().importNode(elementToImport, true);
		tags.appendChild(importedEntity);
	}

	private Element createDocElement(String tagName, Element parentToAppend) {
		Element newDocElement = gameXMLFactory.getDocument().createElement(tagName);
		parentToAppend.appendChild(newDocElement);
		return newDocElement;
	}
}
