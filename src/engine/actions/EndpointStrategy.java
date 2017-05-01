package engine.actions;

import engine.entities.Entity;
import javafx.geometry.Point2D;

/**
 * For modifying the speed based on whether the TraverseLinePathAction's is  reversible. Part of
 * Strategy Design Pattern.
 * @author Matthew Barbano
 *
 */
public interface EndpointStrategy {
	
	/**
	 * The substituted strategy for detecting whether entity is at an endpoint and acting appropriately.
	 * @param entity
	 * @param end
	 */
	public void handleEndpoint(Entity entity, Point2D end);
}
