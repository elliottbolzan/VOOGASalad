package engine.graphics;

import engine.Entity;
import engine.EntityInterface;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Jay Doherty
 * 
 * This class can convert GameEngine Entities into JavaFX Nodes.
 */
public class NodeFactory implements NodeFactoryInterface {

	private String dataFolderPath;
	
	public NodeFactory(String dataFolderPath) {
		this.dataFolderPath = dataFolderPath;
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
		//TODO: remove
		if(getClass().getClassLoader().getResourceAsStream(imagePath) != null) {
			return new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(imagePath)));
		}
		return new ImageView(new Image("file:" + dataFolderPath + imagePath));
	}
}
