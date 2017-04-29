package engine.entities.entities;

import authoring.canvas.Canvas;
import authoring.canvas.EntityView;
import authoring.canvas.LayerEditor;
import engine.entities.Entity;

/**
 * @author Jay Doherty
 *
 */
public class BackgroundEntity extends Entity {

	public BackgroundEntity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setupDefaultParameters() {
		defaultSetup();
		this.setName("Background");
		this.setImagePath(getClass().getClassLoader().getResource("resources/images/background_cave.png").toExternalForm());
		this.setIsVisible(false);
	}

	@Override
	public void addEntityToCanvas(Canvas canvas, LayerEditor editor, EntityView addedEntityView, int z) {
		canvas.removeEntity(editor.getLevelBackground());
		editor.setLevelBackground(addedEntityView);
	}
}
