package engine.actions;

import engine.Action;
import engine.Parameter;

public class JumpSpeedAction extends Action {
	
	public JumpSpeedAction(){
		addParam(new Parameter("Initial Jump Speed", Double.class, 0));
	}

	@Override
	public void act() {
		getEntity().setYSpeed(-Math.abs((Double) getParam("Initial Jump Speed")));
	}
}