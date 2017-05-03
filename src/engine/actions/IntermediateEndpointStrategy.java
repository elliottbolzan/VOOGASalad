package engine.actions;

import engine.entities.Entity;
import javafx.geometry.Point2D;

/**
 * For modifying the speed when a TraverseLinePathAction's is reversible. Part
 * of Strategy Design Pattern.
 * 
 * @author Matthew Barbano
 *
 */

public class IntermediateEndpointStrategy implements EndpointStrategy {
	private Point2D start;
	private boolean approachingEnd;

	public IntermediateEndpointStrategy(Point2D start) {
		this.start = start;
		this.approachingEnd = true;
	}

	@Override
	public void handleEndpoint(Entity entity, Point2D end) {
		Point2D pointToUse = approachingEnd ? end : start;
		if (Math.abs(entity.getX() - pointToUse.getX()) < TraversePathHelper.ZERO_THRESHOLD
				&& Math.abs(entity.getY() - pointToUse.getY()) < TraversePathHelper.ZERO_THRESHOLD) {
			entity.setXSpeed(entity.getXSpeed() * -1);
			entity.setYSpeed(entity.getYSpeed() * -1);
			approachingEnd = !approachingEnd;
		}
	}
}
