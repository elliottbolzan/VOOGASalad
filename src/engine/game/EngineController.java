package engine.game;

import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import engine.Action;
import engine.Entity;
import engine.Event;
import engine.entities.CharacterEntity;

/**
 * @author nikita This class is used for communication between game engine and
 *         the authoring environment. This class is also used when loading
 *         entities from the XML file, in order to create entities in a robust
 *         way.
 */
public class EngineController {

	private ClassFinder finder;
	private ResourceBundle resources;

	public EngineController() {
		finder = new ClassFinder();
	}

	public List<String> getAllEntities() {
		return findClasses("engine.entities", "Entity");
	}

	/**
	 * Get all actions that are accessible for this entity.
	 */
	public List<String> getAllActions(Entity entity) {
		List<String> ret = findClasses("engine.actions", "Action");
		ret.addAll(entity.getAdditionalActions());
		return ret;
	}

	/**
	 * Get all events that are accessible for this entity.
	 */
	public List<String> getAllEvents(Entity entity) {
		List<String> ret = findClasses("engine.events", "Event");
		ret.addAll(entity.getAdditionalEvents());
		return ret;
	}

	public Entity getDefaultEntity() {
		return new CharacterEntity();
	}

	public Entity createEntity(String entity) {
		return (Entity) getInstance("engine.entities." + getClassName(entity, "Entity"), "Entity");

	}

	public Event createEvent(String event) {
		return (Event) getInstance("engine.events.regular_events" + getClassName(event, "Event"), "Event");
	}

	public Action createAction(String action) {
		return (Action) getInstance("engine.actions.regular_actions" + getClassName(action, "Action"), "Action");
	}

	private String getClassName(String string, String type) {
		resources = ResourceBundle.getBundle("resources/Strings");
		Enumeration<String> enumeration = resources.getKeys();
		while (enumeration.hasMoreElements()) {
			String className = enumeration.nextElement();
			if (resources.getString(className).equals(string)) {
				return className;
			}
		}
		return "";
	}

	private List<String> findClasses(String path, String type) {
		List<Class<?>> classes = finder.find(path);
		return classes.stream().map(s -> {
			if (!s.isAnonymousClass()) {
				resources = ResourceBundle.getBundle("resources/Strings");
				return resources.getString(s.getSimpleName().toString());
			}
			return null;
		}).filter(p -> p != null).collect(Collectors.toList());
	}

	private Object getInstance(String path, String type) {
		try {
			Class<?> clazz = Class.forName(path);
			Constructor<?> ctor = clazz.getDeclaredConstructor();
			return ctor.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

}
