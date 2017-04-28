package engine.events.regular_events;

import engine.entities.entities.CameraEntity;
import engine.events.Event;

/**
 * Checks to see if the Entity associated with this Event is within the region
 * of the CameraEntity chosen for their game. act() returns true if the
 * associated Entity is in the camera region and false otherwise.
 * 
 * @author Kyle Finke
 *
 */
public class InCameraRegionEvent extends Event {

	@Override
	public boolean act() {
		CameraEntity camera = getGameInfo().getGraphicsEngine().getCamera();
		return !(getEntity().getX() > camera.getX() + camera.getWidth()
				|| getEntity().getX() + getEntity().getWidth() < camera.getX()
				|| getEntity().getY() > camera.getY() + camera.getHeight()
				|| getEntity().getY() + getEntity().getHeight() < camera.getY());
	}

}
