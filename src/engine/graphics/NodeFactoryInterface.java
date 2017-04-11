package engine.graphics;

import engine.Entity;
import engine.EntityInterface;
import javafx.scene.Node;

public interface NodeFactoryInterface{
	public Node getNodeFromEntity(Entity entity); //: takes Entity and returns Node. 
}
