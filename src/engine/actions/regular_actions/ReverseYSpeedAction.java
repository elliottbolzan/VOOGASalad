package engine.actions.regular_actions;

import engine.actions.Action;

/**
 * Change the direction of velocity in the y direction
 * 
 * @author Kyle Finke
 *
 */
public class ReverseYSpeedAction extends Action {

	@Override
	public void act() {
		getEntity().setYSpeed(-getEntity().getYSpeed());
	}

}
