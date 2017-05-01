package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;

/**
 * Changes the y-speed of the associated Entity to the value associated with the
 * "Y Speed" Parameter
 * 
 * @author Kyle Finke
 *
 */
public class SetYSpeedAction extends Action {

	public SetYSpeedAction() {
		addParam(new Parameter(getResource("YSpeed"), Double.class, 0.0));
	}

	@Override
	public void act() {
		getEntity().setYSpeed(((Double) getParam(getResource("YSpeed"))));
	}
}