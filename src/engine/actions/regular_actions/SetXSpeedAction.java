package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;

/**
 * Sets the x speed of the associated Entity to the value of the Parameter "X
 * Speed"
 * 
 * @author Kyle Finke
 *
 */
public class SetXSpeedAction extends Action {

	public SetXSpeedAction() {
		addParam(new Parameter(getResource("XSpeed"), Double.class, 0.0));
	}

	@Override
	public void act() {
		getEntity().setXSpeed((Double) getParam(getResource("XSpeed")));
	}
}