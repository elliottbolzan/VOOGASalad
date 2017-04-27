package engine.actions.regular_actions;

import engine.Parameter;
import engine.actions.Action;
import engine.entities.Entity;

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