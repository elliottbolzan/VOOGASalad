package engine.entities.entities;

import authoring.canvas.Canvas;
import authoring.canvas.EntityView;
import authoring.canvas.LayerEditor;
import engine.entities.Entity;

/**
 * This type of Entity is used to translate the display as the character
 * progresses through the level.
 * 
 * @author Jay Doherty
 *
 */
public class CameraEntity extends Entity {
	
	@Override
	protected void setupDefaultParameters() {
		defaultSetup();
		this.setName("Camera");
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/camera.png").toExternalForm());
		this.setIsVisible(false);
	}
	
	@Override
	public void addEntityToCanvas(Canvas canvas, LayerEditor editor, EntityView addedEntityView, int z) {
		canvas.removeEntity(editor.getLevelCamera());
		editor.setLevelCamera(addedEntityView);
	}
}
