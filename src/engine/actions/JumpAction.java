package engine.actions;

import engine.Action;
import engine.Entity;
import engine.Parameter;

public class JumpAction extends Action {
	
	public JumpAction(){
		addParam(new Parameter("Initial Jump Speed", Double.class, 0));
	}

	@Override
	public void act() {
		System.out.println("JUMP: " + getEntity().getYSpeed());
		getEntity().setYSpeed(-((Double) getParam("Initial Jump Speed")));
		getEntity().setYAcceleration(Entity.ACCELERATION);
		System.out.println(getEntity().getYSpeed());
	}
}