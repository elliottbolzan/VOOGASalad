package engine.actions;

import engine.Action;
import engine.Parameter;

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

	public void act() {
		getEntity().setX(getEntity().getX() + (Double) getParam("Move amount"));
	}
}