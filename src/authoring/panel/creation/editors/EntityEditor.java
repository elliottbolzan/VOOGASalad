package authoring.panel.creation.editors;

import java.util.List;

import authoring.Workspace;
import engine.Entity;
import engine.Event;
import engine.Parameter;
import engine.game.EngineController;

/**
 * @author Elliott Bolzan
 *
 */
public class EntityEditor extends Editor {

	private EngineController engine;
	private Entity entity;

	/**
	 * @param workspace
	 * @param titleProperty
	 * @param elements
	 * @param object
	 */
	public EntityEditor(Workspace workspace, Entity entity, List<String> elements) {
		super(workspace, elements, entity, false);
		this.entity = entity;
		engine = new EngineController();
	}

	@Override
	public void selected(String string) {
		List<Event> events = entity.getEvents();
		entity = engine.createEntity(string);
		entity.setEvents(events);
		update(entity);
	}

	@Override
	public void save(List<Parameter> data) {}
	
	public Entity getEntity() {
		return entity;
	}

}
