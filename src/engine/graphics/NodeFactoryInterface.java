package engine.graphics;

import engine.entities.Entity;
import javafx.scene.Node;

/**
 * @author Jay Doherty
 *
 * This is a very simple Factory that takes a game engine Entity object and returns
 * a JavaFX Node for displaying the entity on screen.
 */
public interface NodeFactoryInterface{
	public Node getNodeFromEntity(Entity entity);
}
