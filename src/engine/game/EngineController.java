package engine.game;

import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import engine.Event;
import engine.entities.CharacterEntity;
import engine.Action;
import engine.Entity;

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

	public List<String> getAllActions() {
		return findClasses("engine.actions", "Action");
	}

	public List<String> getAllEvents() {
		return findClasses("engine.events", "Event");
	}

	public Entity getDefaultEntity() {
		return new CharacterEntity();
	}

	public Entity createEntity(String entity) {
		return (Entity) getInstance("engine.entities." + getClassName(entity, "Entity"), "Entity");

	}

	public Event createEvent(String event) {
		return (Event) getInstance("engine.events." + getClassName(event, "Event"), "Event");
	}

	public Action createAction(String action) {
		return (Action) getInstance("engine.actions." + getClassName(action, "Action"), "Action");
	}

	private String getClassName(String string, String type) {
		resources = ResourceBundle.getBundle("resources/" + type);
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
			resources = ResourceBundle.getBundle("resources/" + type);
			return resources.getString(s.getSimpleName().toString());
		}).collect(Collectors.toList());
	}

	private Object getInstance(String path, String type) {
		try {
			Class<?> clazz = Class.forName(path);
			Constructor<?> ctor = clazz.getDeclaredConstructor();
			return ctor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
