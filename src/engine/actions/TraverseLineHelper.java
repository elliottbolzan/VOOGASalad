package engine.actions;

import engine.entities.Entity;
import javafx.geometry.Point2D;

/**
 * Subclass for traversing a path via a line (either one to the end, or back and
 * forth between endpoints).
 * 
 * @author Matthew Barbano
 *
 */

public class TraverseLineHelper extends TraversePathHelper {
	private EndpointStrategy endpointStrategy;

	public TraverseLineHelper(Point2D end, double speed, Entity entity, boolean reversible) {
		super(end, speed, entity);
		if (reversible) {
			endpointStrategy = new IntermediateEndpointStrategy(new Point2D(entity.getX(), entity.getY()));
		} else {
			endpointStrategy = new LastEndpointStrategy();
		}
	}

	@Override
	protected void setupPhysics() {
		getEntity().setXAcceleration(0.0);
		getEntity().setYAcceleration(0.0);

		double deltaX = getEnd().getX() - getEntity().getX();
		double deltaY = getEnd().getY() - getEntity().getY();
		double angle = Math.atan2(deltaY, deltaX);
		getEntity().setXSpeed(getSpeed() * Math.cos(angle));
		getEntity().setYSpeed(getSpeed() * Math.sin(angle));
	}

	@Override
	public void updatePhysics() {
		endpointStrategy.handleEndpoint(getEntity(), getEnd());
	}
}
