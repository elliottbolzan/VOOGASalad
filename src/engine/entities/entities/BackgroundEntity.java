package engine.entities.entities;

import authoring.canvas.Canvas;
import authoring.canvas.EntityView;
import authoring.canvas.LayerEditor;
import engine.entities.Entity;

/**
 * @author Jay Doherty
 *
 * A BackgroundEntity represents information about a the background in a given Level. There can only
 * ever be one BackgroundEntity on a given layer of the canvas, so when it is added it removes
 * the previous BackgroundEntity, if one exists.
 */
public class BackgroundEntity extends Entity {

	@Override
	protected void setupDefaultParameters() {
		defaultSetup();
		this.setName(getResource("BackgroundEntity"));
		this.setImagePath(
				getClass().getClassLoader().getResource("resources/images/background_cave.png").toExternalForm());
		this.setIsVisible(false);
	}

	@Override
	public void addEntityToCanvas(Canvas canvas, LayerEditor editor, EntityView addedEntityView, int z) {
		canvas.removeEntity(editor.getLevelBackground());
		editor.setLevelBackground(addedEntityView);
	}
}
