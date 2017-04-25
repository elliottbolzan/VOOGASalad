package engine.actions.regular_actions;

import engine.Action;

/**
 * Sets the horizontal speed of the corresponding Entity to zero.
 * 
 * @author Kyle Finke
 *
 */
public class ZeroRightSpeedAction extends Action {

	@Override
	public void act() {
		if(getEntity().getXSpeed() > 0) {
			getEntity().setXSpeed(0);
		}
	}
}