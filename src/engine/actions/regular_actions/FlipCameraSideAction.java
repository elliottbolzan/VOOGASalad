package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;
import engine.entities.Entity;

/**
 * If the associated Entity is off one side of the Camera and the proper
 * Parameter is true, the Entity flips to the other side of the Camera.
 * 
 * @author Kyle Finke
 *
 */
public class FlipCameraSideAction extends Action {

	public FlipCameraSideAction() {
		addParam(new Parameter("Flip Right Side", boolean.class, true));
		addParam(new Parameter("Flip Left Side", boolean.class, true));
		addParam(new Parameter("Flip Top Side", boolean.class, true));
		addParam(new Parameter("Flip Bottom Side", boolean.class, true));
	}

	@Override
	public void act() {
		Entity camera = getGameInfo().getGraphicsEngine().getCamera();
		if (getEntity().getX() > camera.getX() + camera.getWidth() && (boolean) getParam("Flip Right Side")) {
			getEntity().setX(camera.getX());
		} else if (getEntity().getX() + getEntity().getWidth() < camera.getX()
				&& (boolean) getParam("Flip Left Side")) {
			getEntity().setX(camera.getX() + camera.getWidth() - getEntity().getWidth());
		} else if (getEntity().getY() > camera.getY() + camera.getHeight() && (boolean) getParam("Flip Bottom Side")) {
			getEntity().setY(camera.getY());
		} else if (getEntity().getY() + getEntity().getHeight() < camera.getY()
				&& (boolean) getParam("Flip Top Side")) {
			getEntity().setY(camera.getY() + camera.getHeight() - getEntity().getHeight());
			;
		}
	}

}
