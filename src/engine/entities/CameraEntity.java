package engine.entities;

import engine.Entity;

/**
 * This type of Entity is used to translate the display as the character
 * progresses through the level.
 * 
 * @author Jay Doherty
 *
 */
public class CameraEntity extends Entity {
	
	public CameraEntity() {		
		}

	@Override
	protected void setupDefaultParameters() {
		this.setName("Camera");
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/camera.png").toExternalForm());
		this.setIsVisible(false);
	}


	
}
