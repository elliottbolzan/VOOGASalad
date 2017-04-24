package engine.actions;

import engine.Action;
import engine.Parameter;

public class SetVisibleAction extends Action {

	public SetVisibleAction() {
		addParam(new Parameter("Visible", boolean.class, true));
	}

	@Override
	public void act() {
		getEntity().setIsVisible((boolean) getParam("Visible"));
	}
}
