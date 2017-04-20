package engine.entities;

import engine.Entity;
import engine.actions.MoveAction;
import engine.events.AlwaysEvent;

/**
 * This type of Entity is used to translate the display as the character progresses through the level.
 * @author Jay Doherty
 *
 */
public class CameraEntity extends Entity {

	public CameraEntity() {
		//this.setIsVisible(false); TODO: for now let's make cameras visible so its easy to see whats happening
		this.setupAlwaysMoveEvent();
	}
	
	private void setupAlwaysMoveEvent() {
		MoveAction move = new MoveAction();
		move.setEntity(this);
		
		AlwaysEvent always = new AlwaysEvent();
		always.addAction(move);
		
		this.addEvent(always);
	}
}
