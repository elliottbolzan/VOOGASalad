package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;

public class EntityVisibleAction extends Action {

	public EntityVisibleAction() {
		addParam(new Parameter(getResource("Visible"), boolean.class, true));
	}

	@Override
	public void act() {
		getEntity().setIsVisible((boolean) getParam(getResource("Visible")));
	}
}
