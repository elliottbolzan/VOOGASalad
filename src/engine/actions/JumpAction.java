package engine.actions;

import engine.Action;
import engine.Parameter;

public class JumpAction extends Action {
	
	public JumpAction(){
		addParam(new Parameter("Initial Jump Speed", Double.class, 0));
	}

	@Override
	public void act() {
		getEntity().setYSpeed(-((Double) getParam("Initial Jump Speed")));
	}
}