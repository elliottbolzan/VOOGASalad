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
		Element entitiesElement = this.wrapEntityListInXMLTags(entityList);
		levelElement.appendChild(entitiesElement);

		this.importElement("Camera", camera, levelElement);

		return levelElement;
	}

	protected Element wrapEntityListInXMLTags(List<Element> entityList) {
		Element entitiesElement = gameXMLFactory.getDocument().createElement("Entities");

		for (int i = 0; i < entityList.size(); i++) {
			// for (Element entityElement : entityList) {
			Element entityElement = entityList.get(i);
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
