package engine.actions;

import engine.entities.Entity;
import javafx.geometry.Point2D;

/**
 * Superclass for traversing paths in any shape given by subclass.
 * @author Matthew Barbano
 *
 */

public abstract class TraversePathHelper {
	public static final double ZERO_THRESHOLD = 3; //number of pixels
	
	private Point2D end;
	private double speed;
	private Entity entity;
	
	public TraversePathHelper(Point2D end, double speed, Entity entity){
		this.end = end;
		this.speed = speed;
		this.entity = entity;
		setupPhysics();
	}

	public Point2D getEnd() {
		return end;
	}

	public void setEnd(Point2D end) {
		this.end = end;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	protected abstract void setupPhysics();
	
	public abstract void updatePhysics();

	public double getZeroThreshold() {
		return ZERO_THRESHOLD;
	}
}
