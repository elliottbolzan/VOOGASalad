package engine.events.regular_events;

import java.util.List;

import engine.GameInfo;
import engine.Parameter;
import engine.entities.Entity;
import engine.events.Event;
import javafx.scene.input.KeyCode;

/**
 * This event includes a KeyPressEvent and a CollisionEvent and only triggers if
 * both of them are triggered.
 * 
 * @author Jay Doherty
 *
 */
public class KeyPressAndCollisionEvent extends Event {

	private KeyPressEvent keyPressEvent;
	private CollisionAllEvent collisionEvent;

	public KeyPressAndCollisionEvent() {
		addParam(new Parameter(getResource("Key"), KeyCode.class, KeyCode.UNDEFINED));
		addParam(new Parameter(getResource("Entity1"), String.class, getResource("ThisEntity")));
		addParam(new Parameter(getResource("Entity2"), String.class, ""));
		addParam(new Parameter(getResource("DetectionDepth"), double.class, 0.0));
		keyPressEvent = new KeyPressEvent();
		collisionEvent = new CollisionAllEvent();
	}

	@Override
	public void setEntity(Entity entity) {
		super.setEntity(entity);
		keyPressEvent.setEntity(entity);
		collisionEvent.setEntity(entity);
	}

	@Override
	public void setGameInfo(GameInfo info) {
		super.setGameInfo(info);
		keyPressEvent.setGameInfo(info);
		collisionEvent.setGameInfo(info);
	}

	@Override
	public void setParams(List<Parameter> params) {
		super.setParams(params);
		keyPressEvent.setParams(params);
		collisionEvent.setParams(params);
	}

	@Override
	public boolean act() {
		return keyPressEvent.act() && collisionEvent.act();
	}
}
