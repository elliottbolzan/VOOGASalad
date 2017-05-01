package engine.game;

import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import engine.actions.Action;
import engine.entities.Entity;
import engine.entities.entities.CharacterEntity;
import engine.events.Event;

/**
 * @author nikita This class is used for communication between game engine and
 *         the authoring environment. This class is also used when loading
 *         entities from the XML file, in order to create entities in a robust
 *         way.
 */
public class EngineController {
	private ClassFinder finder;
	private ResourceBundle resources;
	private int entityIds, eventIds, actionIds;

	public EngineController() {
		finder = new ClassFinder();
		resources = ResourceBundle.getBundle("resources/Strings");
		entityIds = 0;
		eventIds = 0;
		actionIds = 0;
	}

	public List<String> getAllEntities() {
		return findClasses("engine.entities.entities");
	}

	/**
	 * Get all actions that are accessible for this entity.
	 */
	public List<String> getAllActions(Entity entity) {
		return getNames(entity, "actions");
	}

	/**
	 * Get all events that are accessible for this entity.
	 */
	public List<String> getAllEvents(Entity entity) {
		return getNames(entity, "events");
	}

	private List<String> getNames(Entity entity, String type) {
		List<String> ret = findClasses("engine." + type + ".regular_" + type);
		List<String> additional = type.equals("events") ? entity.getAdditionalEvents() : entity.getAdditionalActions();
		ret.addAll(additional.stream().map(s -> resources.getString(s)).collect(Collectors.toList()));
		return ret;
	}

	public Entity getDefaultEntity() {
		return new CharacterEntity();
	}

	public Entity createEntity(String entity) throws Exception {
		Entity ret = (Entity) getInstance("engine.entities.entities." + getClassName(entity));
		ret.setId(entityIds++);
		return ret;
	}

	public Event createEvent(String event) throws Exception {
		Event ret;
		try {
			ret = (Event) getInstance("engine.events.regular_events." + getClassName(event));
		} catch (Exception e) {
			ret = (Event) getInstance("engine.events.additional_events." + getClassName(event));
		}
		ret.setId(eventIds++);
		return ret;
	}

	public Action createAction(String action) throws Exception {
		Action ret = null;
		try {
			ret = (Action) getInstance("engine.actions.regular_actions." + getClassName(action));
		} catch (Exception e) {
			ret = (Action) getInstance("engine.actions.additional_actions." + getClassName(action));
		}
		ret.setId(actionIds++);
		return ret;
	}

	private String getClassName(String string) {
		Enumeration<String> enumeration = resources.getKeys();
		while (enumeration.hasMoreElements()) {
			String className = enumeration.nextElement();
			if (resources.getString(className).equals(string)) {
				return className;
			}
		}
		return "";
	}

	private List<String> findClasses(String path) {
		List<Class<?>> classes = finder.find(path);
		return classes.stream().map(s -> {
			if (!s.isAnonymousClass()) {
				return resources.getString(s.getSimpleName().toString());
			}
			return null;
		}).filter(p -> p != null).collect(Collectors.toList());
	}

	private Object getInstance(String path) throws Exception {
		Class<?> clazz = Class.forName(path);
		Constructor<?> ctor = clazz.getDeclaredConstructor();
		return ctor.newInstance();
	}
}
