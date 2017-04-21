package engine.entities;

import engine.Entity;

/**
 * This type of Entity is used to translate the display as the character progresses through the level.
 * @author Jay Doherty
 *
 */
public class CameraEntity extends Entity {
	
	public CameraEntity() {		
		this.setName("Camera");
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/camera.png").toExternalForm());
		//this.setIsVisible(false); //TODO: for now leave the camera image visible for testing
		this.setZ(-1);
		//this.setupAlwaysMoveEvent();
	}

	/*private void setupAlwaysMoveEvent() {
		MoveAction move = new MoveAction();
		move.setEntity(this);
		
		AlwaysEvent always = new AlwaysEvent();
		always.addAction(move);
		
		this.addEvent(always);
	}*/
}
