package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;

/**
 * Set the bearing of this entity to the specified angle.
 * 
 * @author nikita
 */
public class SetHeadingAction extends Action {

	public SetHeadingAction() {
		addParam(new Parameter(getResource("Heading"), double.class, 0.0));
	}

	@Override
	public void act() {
		getEntity().setRotate((double) getParam(getResource("Heading")));
	}
}
