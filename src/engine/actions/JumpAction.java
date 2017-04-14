package engine.actions;

import engine.Action;
import engine.Entity;
import engine.Parameter;

/**
 * Changes the y-speed of the associated Entity to the value associated with the
 * "Initial Jump Speed" Parameter
 * 
 * @author Kyle Finke
 *
 */
public class JumpAction extends Action {

	public JumpAction() {
		addParam(new Parameter("Initial Jump Speed", Double.class, 0));
	}

	@Override
	public void act() {
		getEntity().setYSpeed(-((Double) getParam("Initial Jump Speed")));
		getEntity().setYAcceleration(Entity.YACCELERATION);
	}
}