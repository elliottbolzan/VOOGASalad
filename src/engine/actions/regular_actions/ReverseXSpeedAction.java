package engine.actions.regular_actions;

import engine.actions.Action;

/**
 * Change the direction of velocity in the x direction
 * 
 * @author Kyle Finke
 *
 */
public class ReverseXSpeedAction extends Action {

	@Override
	public void act() {
		getEntity().setXSpeed(-getEntity().getXSpeed());
	}

}
