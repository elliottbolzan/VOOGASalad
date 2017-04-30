package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;
import engine.entities.Entity;

/**
 * Moves the associated Entity in the x direction by the amount stored in the
 * "Walk Speed" Parameter.
 * 
 * @author Kyle Finke
 *
 */
public class WalkAction extends Action {

	public WalkAction() {
		addParam(new Parameter("Walk Speed", Double.class, 0.0));
	}

	@Override
	public void act() {
		Entity entity = getEntity();
		double velocity = ((Double) getParam("Walk Speed"));
		entity.setXSpeed(velocity);
	}
}