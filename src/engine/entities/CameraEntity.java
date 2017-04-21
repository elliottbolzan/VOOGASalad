package engine.entities;

import engine.Entity;
import engine.actions.FollowAction;
import engine.events.AlwaysEvent;

/**
 * This type of Entity is used to translate the display as the character progresses through the level.
 * @author Jay Doherty
 *
 */
public class CameraEntity extends Entity {
	
	public CameraEntity() {		
		//this.setWidth(600);
		//this.setHeight(600);
		this.setName("Camera");
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/camera.png").toExternalForm());
		this.setZ(-1);
		//this.setIsVisible(false); //TODO: for now leave the camera image visible for testing
		//this.setupAlwaysMoveEvent();
		//this.setupAlwaysFollowEvent();
	}

	/*private void setupAlwaysMoveEvent() {
		MoveAction move = new MoveAction();
		move.setEntity(this);
		
		AlwaysEvent always = new AlwaysEvent();
		always.addAction(move);
		
		this.addEvent(always);
	}*/
	
	private void setupAlwaysFollowEvent() {
		FollowAction follow = new FollowAction();
		follow.setEntity(this);
		follow.updateParam("Leader Entity", "Mario");
		
		AlwaysEvent always = new AlwaysEvent();
		always.addAction(follow);
		
		this.addEvent(always);
	}
}
