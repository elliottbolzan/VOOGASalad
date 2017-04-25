package engine.events.regular_events;

import java.util.List;

import engine.Entity;
import engine.Event;
import engine.GameInfo;
import engine.Parameter;
import javafx.scene.input.KeyCode;

/**
 * This event includes a KeyPressEvent and a CollisionEvent and only triggers if both of them are triggered.
 * 
 * @author Jay Doherty
 *
 */
public class KeyPressAndCollisionEvent extends Event {

	private KeyPressEvent keyPressEvent;
	private CollisionEvent collisionEvent;
	
	public KeyPressAndCollisionEvent() {
		addParam(new Parameter("Key", KeyCode.class, KeyCode.UNDEFINED));
		addParam(new Parameter("Entity", String.class, ""));
		keyPressEvent = new KeyPressEvent();
		collisionEvent = new CollisionEvent();
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
