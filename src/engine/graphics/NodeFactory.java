package engine.graphics;

import engine.Entity;
import engine.EntityInterface;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Jay Doherty
 * 
 * This class takes game engine Entities and returns a corresponding JavaFX ImageView to display on screen.
 */
public class NodeFactory implements NodeFactoryInterface {
	
	public NodeFactory() {

	}

	@Override
	public ImageView getNodeFromEntity(Entity entity) {
		ImageView node = this.getImageFromPath(entity.getImagePath());
		node.setX(entity.getX());
		node.setY(entity.getY());
		node.setFitWidth(entity.getWidth());
		node.setFitHeight(entity.getHeight());
		return node;
	}
	
	private ImageView getImageFromPath(String imagePath) {
		//TODO: remove the following
		if(getClass().getClassLoader().getResourceAsStream(imagePath) != null) {
			return new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(imagePath)));
		}
		return new ImageView(new Image(imagePath));
	}
}
