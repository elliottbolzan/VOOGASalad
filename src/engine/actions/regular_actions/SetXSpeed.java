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
public class SetXSpeed extends Action {

	public SetXSpeed() {
		addParam(new Parameter("X Speed", Double.class, 0.0));
	}

	@Override
	public void act() {
		getEntity().setXSpeed((Double) getParam("X Speed"));
	}
}