package engine.actions;

import engine.entities.Entity;
import javafx.geometry.Point2D;

public interface EndpointStrategy {
	public void handleEndpoint(Entity entity, Point2D end);
}
