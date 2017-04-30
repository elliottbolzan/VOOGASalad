package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;

/**
 * Move an associated Entity vertically by a specific amount associated with the
 * "Move amount" parameter created in the constructor.
 * 
 * @author Kyle Finke
 *
 */
public class ShiftVerticalAction extends Action {

	public ShiftVerticalAction() {
		addParam(new Parameter(getResource("MoveAmount"), Double.class, 0));
	}

	@Override
	public void act() {
		getEntity().setY(getEntity().getY() + (Double) getParam(getResource("MoveAmount")));
	}

}