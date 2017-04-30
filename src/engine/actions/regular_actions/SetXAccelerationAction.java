package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;

/**
 * Changes the x acceleration of the Entity associated with this Action.
 * 
 * @author Kyle Finke
 *
 */
public class SetXAccelerationAction extends Action {

	public SetXAccelerationAction() {
		addParam(new Parameter("X Acceleration", Double.class, 0));
	}

	@Override
	public void act() {
		getEntity().setXAcceleration((double) getParam("X Acceleration"));
	}

}
