package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;

/**
 * Rotates an entity by a given amount of degrees
 * 
 * @author nikita
 */
public class RotateAction extends Action {

	public RotateAction() {
		addParam(new Parameter(getResource("RotateAmount"), double.class, 0.0));
	}

	@Override
	public void act() {
		getEntity().setRotate(getEntity().getRotate() + (double) getParam(getResource("RotateAmount")));
	}
}
