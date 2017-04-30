package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;

/**
 * Changes the x and/or y speed and/or acceleration of the Entity associated
 * with this Action if the corresponding boolean Parameter is true.
 * 
 * @author Kyle Finke
 *
 */
public class ChangeEntityMotionAction extends Action {

	public ChangeEntityMotionAction() {
		addParam(new Parameter("X Speed", Double.class, 0));
		addParam(new Parameter("Y Speed", Double.class, 0));
		addParam(new Parameter("X Acceleration", Double.class, 0));
		addParam(new Parameter("Y Acceleration", Double.class, 0));
		addParam(new Parameter("Change X Speed", boolean.class, false));
		addParam(new Parameter("Change Y Speed", boolean.class, false));
		addParam(new Parameter("Change X Acceleration", boolean.class, false));
		addParam(new Parameter("Change Y Acceleration", boolean.class, false));
	}

	@Override
	public void act() {
		if ((boolean) getParam("Change X Speed")) {
			getEntity().setXSpeed((double) getParam("X Speed"));
		}
		if ((boolean) getParam("Change Y Speed")) {
			getEntity().setYSpeed((double) getParam("Y Speed"));
		}
		if ((boolean) getParam("Change X Acceleration")) {
			getEntity().setXAcceleration((double) getParam("X Acceleration"));
		}
		if ((boolean) getParam("Change Y Acceleration")) {
			getEntity().setYAcceleration((double) getParam("Y Acceleration"));
		}
	}

}
