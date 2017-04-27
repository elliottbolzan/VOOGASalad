package engine.graphics;

import engine.entities.Entity;
import javafx.scene.Node;

public interface NodeFactoryInterface{
	public Node getNodeFromEntity(Entity entity); //: takes Entity and returns Node. 
}
