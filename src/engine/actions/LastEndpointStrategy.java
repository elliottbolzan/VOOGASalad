package engine.actions;

import engine.entities.Entity;
import javafx.geometry.Point2D;

/**
 * For modifying the speed when a TraverseLinePathAction's is not reversible. Part of
 * Strategy Design Pattern.
 * @author Matthew Barbano
 *
 */

public class LastEndpointStrategy implements EndpointStrategy {

	@Override
	public void handleEndpoint(Entity entity, Point2D end) {
		if (Math.abs(entity.getX() - end.getX()) < TraversePathHelper.ZERO_THRESHOLD
				&& Math.abs(entity.getY() - end.getY()) < TraversePathHelper.ZERO_THRESHOLD) {
			entity.setXSpeed(0.0);
			entity.setYSpeed(0.0);
		}
	}

}
