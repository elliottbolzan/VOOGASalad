package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;

/**
 * Move an associated Entity horizontally by a specific amount associated with
 * the "Move amount" parameter created in the constructor.
 * 
 * @author Kyle Finke
 *
 */
public class ShiftHorizontalAction extends Action {

	public ShiftHorizontalAction() {
		addParam(new Parameter("Move amount", Double.class, 0));
	}

	@Override
	public void act() {
		getEntity().setX(getEntity().getX() + (Double) getParam("Move amount"));
	}
}