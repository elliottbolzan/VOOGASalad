package engine.actions;

import engine.entities.Entity;
import javafx.geometry.Point2D;

public class TraverseLineHelper extends TraversePathHelper {
	
	public TraverseLineHelper(Point2D end, double speed, Entity entity){
		 super(end, speed, entity);
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
	
	protected void handleEnd(){
		 if(Math.abs(getEntity().getX() - getEnd().getX()) < getZeroThreshold() && Math.abs(getEntity().getY() - getEnd().getY()) < getZeroThreshold()){
			 getEntity().setXSpeed(0.0);
			 getEntity().setYSpeed(0.0);
		 }
	}
	
	@Override
	public void updatePhysics() {
		handleEnd();
	}
}
